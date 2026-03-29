package ru.nsu.astakhov.autodocs.document.generator;

import com.github.petrovich4j.Gender;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.PreparedTemplateInfo;
import ru.nsu.astakhov.autodocs.exceptions.GenderResolutionException;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.regex.Matcher;

import static java.util.Map.entry;

public class DocumentGenerator extends AbstractGenerator<StudentDto> {
    private final RussianWordDecliner russianWordDecliner;
    private final PreparedTemplateInfo preparedTemplateInfo;

    public DocumentGenerator(PreparedTemplateInfo preparedTemplateInfo) {
        this.russianWordDecliner = new RussianWordDecliner();
        this.preparedTemplateInfo = preparedTemplateInfo;

        initOutputDirectory();
    }

    private static final Map<String, Function<StudentDto, String>> RESOLVERS = Map.ofEntries(
            entry("fullName", StudentDto::fullName),
            entry("course", tempDto -> String.valueOf(tempDto.course().getValue())),
            entry("email", StudentDto::email),
            entry("phoneNumber", StudentDto::phoneNumber),
            entry("eduProgram", tempDto -> tempDto.eduProgram().getValue()),
            entry("groupName", StudentDto::groupName),
            entry("specialization", tempDto -> tempDto.specialization().getValue()),
            entry("orderOnApprovalTopic", StudentDto::orderOnApprovalTopic),
            entry("orderOnCorrectionTopic", StudentDto::orderOnCorrectionTopic),
            entry("actualSupervisor", StudentDto::actualSupervisor),
            entry("thesisCoSupervisor", StudentDto::thesisCoSupervisor),
            entry("thesisConsultant", StudentDto::thesisConsultant),
            entry("thesisTopic", StudentDto::thesisTopic),
            entry("reviewer", StudentDto::reviewer),
            entry("internshipType", tempDto -> tempDto.internshipType().getValue()),
            entry("thesisSupervisor.name", tempDto -> tempDto.thesisSupervisor().name()),
            entry("thesisSupervisor.position", tempDto -> tempDto.thesisSupervisor().position()),
            entry("thesisSupervisor.degree", tempDto -> tempDto.thesisSupervisor().degree()),
            entry("thesisSupervisor.title", tempDto -> tempDto.thesisSupervisor().title()),
            entry("fullOrganizationName", StudentDto::fullOrganizationName),
            entry("NSUSupervisor.name", tempDto -> tempDto.NSUSupervisor().name()),
            entry("NSUSupervisor.position", tempDto -> tempDto.NSUSupervisor().position()),
            entry("NSUSupervisor.degree", tempDto -> tempDto.NSUSupervisor().degree()),
            entry("NSUSupervisor.title", tempDto -> tempDto.NSUSupervisor().title()),
            entry("organizationSupervisor.name", tempDto -> tempDto.organizationSupervisor().name()),
            entry("organizationSupervisor.position", tempDto -> tempDto.organizationSupervisor().position()),
            entry("organizationSupervisor.degree", tempDto -> tempDto.organizationSupervisor().degree()),
            entry("organizationSupervisor.title", tempDto -> tempDto.organizationSupervisor().title()),
            entry("administrativeActFromOrganization", StudentDto::administrativeActFromOrganization),
            entry("fullPlaceOfInternship", StudentDto::fullPlaceOfInternship),
            entry("organizationName", StudentDto::organizationName)
    );

    private static final Map<String, Function<StudentDto, String>> RESOLVERS_RUS = Map.ofEntries(
            entry("полноеИмя", StudentDto::fullName),
            entry("курс", tempDto -> String.valueOf(tempDto.course().getValue())),
            entry("почта", StudentDto::email),
            entry("телефон", StudentDto::phoneNumber),
            entry("образПрограмма", tempDto -> tempDto.eduProgram().getValue()),
            entry("группа", StudentDto::groupName),
            entry("специализация", tempDto -> tempDto.specialization().getValue()),
            entry("утверждениеТемы", StudentDto::orderOnApprovalTopic),
            entry("корректировкаТемы", StudentDto::orderOnCorrectionTopic),
            entry("фактический", StudentDto::actualSupervisor),
            entry("соруководительВКР.имя", StudentDto::thesisCoSupervisor),
            entry("консультантВКР", StudentDto::thesisConsultant),
            entry("темаВКР", StudentDto::thesisTopic),
            entry("рецензент", StudentDto::reviewer),
            entry("видПрактики", tempDto -> tempDto.internshipType().getValue()),
            entry("руководительВКР.имя", tempDto -> tempDto.thesisSupervisor().name()),
            entry("руководительВКР.должность", tempDto -> tempDto.thesisSupervisor().position()),
            entry("руководительВКР.степень", tempDto -> tempDto.thesisSupervisor().degree()),
            entry("руководительВКР.звание", tempDto -> tempDto.thesisSupervisor().title()),
            entry("полноеИмяОрганизации", StudentDto::fullOrganizationName),
            entry("руководительНГУ.имя", tempDto -> tempDto.NSUSupervisor().name()),
            entry("руководительНГУ.должность", tempDto -> tempDto.NSUSupervisor().position()),
            entry("руководительНГУ.степень", tempDto -> tempDto.NSUSupervisor().degree()),
            entry("руководительНГУ.звание", tempDto -> tempDto.NSUSupervisor().title()),
            entry("руководительОрганизации.имя", tempDto -> tempDto.organizationSupervisor().name()),
            entry("руководительОрганизации.должность", tempDto -> tempDto.organizationSupervisor().position()),
            entry("руководительОрганизации.степень", tempDto -> tempDto.organizationSupervisor().degree()),
            entry("руководительОрганизации.звание", tempDto -> tempDto.organizationSupervisor().title()),
            entry("актОтОрганизации", StudentDto::administrativeActFromOrganization),
            entry("местоПрактикиПолностью", StudentDto::fullPlaceOfInternship),
            entry("наименованиеОрганизации", StudentDto::organizationName),
            entry("датаВыдачиЗаданияПрактики", StudentDto::dateOfPracticeAssignment)
    );

    private static final Map<String, BiFunction<StudentDto, RussianWordDecliner, String>> ADDITIONAL_RESOLVERS = Map.ofEntries(
            entry("genitiveStudentForm", (student, decliner) -> {
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
            entry("genitiveFullName", (student, decliner) -> {
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
            entry("studentForm", (student, decliner) -> {
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

    private static final Map<String, BiFunction<StudentDto, RussianWordDecliner, String>> ADDITIONAL_RESOLVERS_RUS = Map.ofEntries(
            entry("полОбучающегося", (student, decliner) -> {
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
            entry("полноеИмяРодительный", (student, decliner) -> {
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
            entry("полОбучающийся", (student, decliner) -> {
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
            Files.createDirectories(Paths.get(preparedTemplateInfo.documentDir()));
        }
        catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для документов", e);
        }
    }

    public void generate(StudentDto dto) {
        String saveName = dto.fullName().replace(' ', '_') + '_' + preparedTemplateInfo.fileName() + ".docx";
        Path outputFilePath = Paths.get(preparedTemplateInfo.documentDir(), saveName);
        generateDocument(outputFilePath, dto);
    }

    private void generateDocument(Path outputFilePath, StudentDto studentDto) {
        try (InputStream inputStream = new FileInputStream(preparedTemplateInfo.templatePath())) {

            try (XWPFDocument document = new XWPFDocument(inputStream);
                 FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
                super.processDocument(document, studentDto);
                document.write(out);
            }

        }
        catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации документа: " + outputFilePath, e);
        }
    }

    @Override
    protected void applyReplacement(XWPFRun run, Matcher matcher, StudentDto studentDto, StringBuilder result) {
        String key = matcher.group(1); // ключ без $()
        applyDefaultReplacement(run, matcher, studentDto, key, result);
    }

    @Override
    protected void applyTableReplacement(XWPFRun run, XWPFTable table, Matcher matcher, StudentDto studentDto, StringBuilder result) {
        String key = matcher.group(1); // ключ без $()
        applyDefaultReplacement(run, matcher, studentDto, key, result);
    }

    private void applyDefaultReplacement(
            XWPFRun run,
            Matcher matcher,
            StudentDto studentDto,
            String key,
            StringBuilder result) {

        Function<StudentDto, String> resolver = RESOLVERS.get(key);
        String value;
        if (resolver == null) {
            resolver = RESOLVERS_RUS.get(key);
        }

        if (resolver != null) {
            value = resolver.apply(studentDto);
        }
        else {
            BiFunction<StudentDto, RussianWordDecliner, String> additionalResolver = ADDITIONAL_RESOLVERS.get(key);
            if (additionalResolver == null) {
                additionalResolver = ADDITIONAL_RESOLVERS_RUS.get(key);
            }
            value = additionalResolver.apply(studentDto, russianWordDecliner);
        }
        // TODO: пометить плейсхолдеры в документах "заявление на практику" жёлтым
        // TODO: исправить уведомление "разрешение конфликтов" при генерации. Оно не исчезает
        if (value == null) {
            throw new IllegalStateException("Value is empty: " + key + ":" + studentDto.fullName() + ":" + studentDto.dateOfPracticeAssignment());
        }
        if (!value.isBlank()) {
            run.setTextHighlightColor("white");
        }
        else if (key.equals("administrativeActFromOrganization")) {
            value = "\"__\" __________ 20__ г. №______";
        }
        else if (key.equals("thesisSupervisor.name") ||
                key.equals("thesisSupervisor.position") ||
                key.equals("thesisSupervisor.degree")) {
            value = "_____________";
            run.setTextHighlightColor("white");
        }

        if (!value.isBlank()) {
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        else {
            matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
        }
    }
}