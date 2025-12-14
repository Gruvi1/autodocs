package ru.nsu.astakhov.autodocs.document.generator;

import com.github.petrovich4j.Gender;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.exceptions.GenderResolutionException;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Map.entry;

public abstract class AbstractDocumentGenerator implements DocumentGenerator {
    private final RussianWordDecliner russianWordDecliner;
    private final String outputFileName;
    private final String templatePath;
    private final String outputDirectory;
    private final List<String> placeholders;

    protected AbstractDocumentGenerator(RussianWordDecliner russianWordDecliner,
                                        String outputFileName, String templatePath,
                                        String outputDirectory, List<String> placeholders) {
        this.russianWordDecliner = russianWordDecliner;
        this.outputFileName = outputFileName;
        this.templatePath = templatePath;
        this.outputDirectory = outputDirectory;
        this.placeholders = placeholders;

        initOutputDirectory();
    }

    protected static final Map<String, Function<StudentDto, String>> RESOLVERS = Map.ofEntries(
            entry("$(fullName)", StudentDto::fullName),
            entry("$(course)", tempDto -> String.valueOf(tempDto.course().getValue())),
            entry("$(email)", StudentDto::email),
            entry("$(phoneNumber)", StudentDto::phoneNumber),
            entry("$(eduProgram)", tempDto -> tempDto.eduProgram().getValue()),
            entry("$(groupName)", StudentDto::groupName),
            entry("$(specialization)", tempDto -> tempDto.specialization().getValue()),
            entry("$(orderOnApprovalTopic)", StudentDto::orderOnApprovalTopic),
            entry("$(orderOnCorrectionTopic)", StudentDto::orderOnCorrectionTopic),
            entry("$(actualSupervisor)", StudentDto::actualSupervisor),
            entry("$(thesisCoSupervisor)", StudentDto::thesisCoSupervisor),
            entry("$(thesisConsultant)", StudentDto::thesisConsultant),
            entry("$(thesisTopic)", StudentDto::thesisTopic),
            entry("$(reviewer)", StudentDto::reviewer),
            entry("$(internshipType)", tempDto -> tempDto.internshipType().getValue()),
            entry("$(thesisSupervisor.name)", tempDto -> tempDto.thesisSupervisor().name()),
            entry("$(thesisSupervisor.position)", tempDto -> tempDto.thesisSupervisor().position()),
            entry("$(thesisSupervisor.degree)", tempDto -> tempDto.thesisSupervisor().degree()),
            entry("$(thesisSupervisor.title)", tempDto -> tempDto.thesisSupervisor().title()),
            entry("$(fullOrganizationName)", StudentDto::fullOrganizationName),
            entry("$(NSUSupervisor.name)", tempDto -> tempDto.NSUSupervisor().name()),
            entry("$(NSUSupervisor.position)", tempDto -> tempDto.NSUSupervisor().position()),
            entry("$(NSUSupervisor.degree)", tempDto -> tempDto.NSUSupervisor().degree()),
            entry("$(NSUSupervisor.title)", tempDto -> tempDto.NSUSupervisor().title()),
            entry("$(organizationSupervisor.name)", tempDto -> tempDto.organizationSupervisor().name()),
            entry("$(organizationSupervisor.position)", tempDto -> tempDto.organizationSupervisor().position()),
            entry("$(organizationSupervisor.degree)", tempDto -> tempDto.organizationSupervisor().degree()),
            entry("$(organizationSupervisor.title)", tempDto -> tempDto.organizationSupervisor().title()),
            entry("$(administrativeActFromOrganization)", StudentDto::administrativeActFromOrganization),
            entry("$(fullPlaceOfInternship)", StudentDto::fullPlaceOfInternship),
            entry("$(organizationName)", StudentDto::organizationName)
    );

    protected static final Map<String, BiFunction<StudentDto, RussianWordDecliner, String>> ADDITIONAL_RESOLVERS = Map.ofEntries(
            entry("$(genitiveStudentForm)", (student, decliner) -> {
                Gender gender = student.gender();
                if (gender != null) {
                    return decliner.getGenitiveStudentFormByGender(gender);
                }
                String[] nameParts = student.fullName().split(" ");
                if (nameParts.length != 3 || (gender = decliner.getGenderByPatronymic(nameParts[2])) == Gender.Both) {
                    throw new GenderResolutionException(student);
                }
                return decliner.getGenitiveStudentFormByGender(gender);
            }),
            entry("$(genitiveFullName)", (student, decliner) -> {
                String fullName = student.fullName();
                Gender gender = student.gender();
                if (gender != null) {
                    return decliner.getFullNameInGenitiveCase(fullName, gender);
                }
                String[] nameParts = fullName.split(" ");
                if (nameParts.length != 3 || (gender = decliner.getGenderByPatronymic(nameParts[2])) == Gender.Both) {
                    throw new GenderResolutionException(student);
                }
                return decliner.getFullNameInGenitiveCase(fullName, gender);
            }),
            entry("$(studentForm)", (student, decliner) -> {
                Gender gender = student.gender();
                if (gender != null) {
                    return decliner.getStudentFormByGender(gender);
                }
                String[] nameParts = student.fullName().split(" ");
                if (nameParts.length != 3 || (gender = decliner.getGenderByPatronymic(nameParts[2])) == Gender.Both) {
                    throw new GenderResolutionException(student);
                }
                return decliner.getStudentFormByGender(gender);
            })
    );

    private void initOutputDirectory() {
        try {
            Files.createDirectories(Paths.get(outputDirectory));
        }
        catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для документов", e);
        }
    }

    @Override
    public void generate(StudentDto dto) {
        String safeName = dto.fullName().replace(' ', '_') + '_' + outputFileName;
        Path outputFilePath = Paths.get(outputDirectory, safeName);
        generateDocument(outputFilePath, dto);
    }

    protected void generateDocument(Path outputFilePath, StudentDto dto) {
        try (InputStream in = getClass().getResourceAsStream(templatePath)) {
            if (in == null) {
                throw new IllegalArgumentException("Шаблон документа не найден по пути: " + templatePath);
            }
            try (XWPFDocument doc = new XWPFDocument(in);
                 FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
                for (XWPFParagraph paragraph : doc.getParagraphs()) {
                    String text = paragraph.getText();
                    if (text != null && containsAny(text, placeholders)) {
                        processParagraph(paragraph, placeholders, dto);
                    }
                }
                doc.write(out);
            }

        }
        catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации документа: " + outputFilePath, e);
        }
    }

    private void processParagraph(XWPFParagraph paragraph, List<String> placeholders, StudentDto dto) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.text();
            if (text == null) {
                continue;
            }

            String updatedText = text;
            String replaceable;

            int count = 0;
            while ((replaceable = findFirstPlaceholder(updatedText, placeholders)) != null) {
                ++count;
                Function<StudentDto, String> resolver = RESOLVERS.get(replaceable);
                String value;
                if (resolver != null) {
                    value = resolver.apply(dto);
                }
                else {
                    BiFunction<StudentDto, RussianWordDecliner, String> additionalResolver = ADDITIONAL_RESOLVERS.get(replaceable);
                    value = additionalResolver.apply(dto, russianWordDecliner);
                }
                // TODO: пометить плейсхолдеры в документах "заявление на практику" жёлтым
                // TODO: исправить уведомление "разрешение конфликтов" при генерации. Оно не исчезает
                if (!value.isEmpty()) {
                    run.setTextHighlightColor("white");
                }
                else if (replaceable.equals("$(administrativeActFromOrganization)")) {
                    value = "\"__\" __________ 2025 г. №______";
                }
                else if (replaceable.equals("$(thesisSupervisor.name)") ||
                replaceable.equals("$(thesisSupervisor.position)") ||
                replaceable.equals("$(thesisSupervisor.degree)")) {
                    value = "_____________";
                    run.setTextHighlightColor("white");
                }
                updatedText = updatedText.replace(replaceable, value);
            }
            if (count > 1) {
                System.out.println(count);
            }

            run.getCTR().setTArray(new CTText[0]);
            run.setText(updatedText);
        }
    }

    private boolean containsAny(String text, List<String> placeholders) {
        for (String placeholder : placeholders) {
            if (text.contains(placeholder)) {
                return true;
            }
        }
        return false;
    }

    private String findFirstPlaceholder(String text, List<String> placeholders) {
        for (String placeholder : placeholders) {
            if (text.contains(placeholder)) {
                return placeholder;
            }
        }
        return null;
    }
}