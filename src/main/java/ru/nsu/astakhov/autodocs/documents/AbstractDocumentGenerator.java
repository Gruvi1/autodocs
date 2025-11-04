package ru.nsu.astakhov.autodocs.documents;

import com.github.petrovich4j.Gender;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.Map.entry;

@Slf4j
@Service
public abstract class AbstractDocumentGenerator {
    private final RussianWordDecliner decliner;
    protected final String documentDirectory = "documents";

    public AbstractDocumentGenerator(RussianWordDecliner decliner) {
        this.decliner = decliner;
        initDocumentDirectory();
    }

    private void initDocumentDirectory() {
        try {
            Files.createDirectories(Paths.get(documentDirectory));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для документов", e);
        }
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
            entry("$(studentForm)", (student, decliner) -> {
                Gender gender = decliner.getGenderByPatronymic(student.fullName().split(" ")[2]);
                return decliner.getStudentFormByGender(gender);
            }),
            entry("$(genitiveFullName)", (student, decliner) -> {
                String fullName = student.fullName();
                String[] parts = fullName.split(" ");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Ожидаются ФИО");
                }
                Gender gender = decliner.getGenderByPatronymic(parts[2]);
                return decliner.getFullNameInGenitiveCase(fullName, gender);
            })
    );

    protected void generateDocument(String templatePath, String outputFileName, List<String> placeholders, StudentDto dto) {
        String newFilePath = documentDirectory + "/" + outputFileName;

        try (InputStream in = getClass().getResourceAsStream(templatePath);
             XWPFDocument doc = new XWPFDocument(in);
             FileOutputStream out = new FileOutputStream(newFilePath)) {

            for (XWPFParagraph paragraph : doc.getParagraphs()) {
                String text = paragraph.getText();
                if (text != null && containsAny(text, placeholders)) {
                    processParagraph(paragraph, placeholders, dto);
                }
            }

            doc.write(out);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации документа: " + outputFileName, e);
        }
    }

    private void processParagraph(XWPFParagraph paragraph, List<String> placeholders, StudentDto dto) {
        for (XWPFRun run : paragraph.getRuns()) {
            String text = run.text();
            if (text == null) continue;

            System.out.println(text);
            String updatedText = text;
            String replaceable;
            while ((replaceable = findFirstPlaceholder(updatedText, placeholders)) != null) {
                Function<StudentDto, String> resolver = RESOLVERS.get(replaceable);
                String value;
                if (resolver != null) {
                    value = resolver.apply(dto);
                }
                else {
                    BiFunction<StudentDto, RussianWordDecliner, String> additionalResolver = ADDITIONAL_RESOLVERS.get(replaceable);
                    value = additionalResolver.apply(dto, decliner);
                }
                updatedText = updatedText.replace(replaceable, value);
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