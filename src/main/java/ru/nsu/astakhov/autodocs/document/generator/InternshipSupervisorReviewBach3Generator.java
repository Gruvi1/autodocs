package ru.nsu.astakhov.autodocs.document.generator;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class InternshipSupervisorReviewBach3Generator extends AbstractDocumentGenerator{
    private static final String OUTPUT_FILE_NAME = "Отзыв_руководителя_практики_Бакалавриат_3курс.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/3rd_course";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(internshipType)",
            "$(studentForm)",
            "$(fullName)",
            "$(groupName)",
            "$(eduProgram)",
            "$(specialization)",
            "$(fullOrganizationName)",
            "$(organizationName)",
            "$(organizationSupervisor.position)",
            "$(organizationSupervisor.name)"
    );


    public InternshipSupervisorReviewBach3Generator(RussianWordDecliner decliner) {
        super(decliner);
        initOutputDirectory();
    }

    private void initOutputDirectory() {
        try {
            Files.createDirectories(Paths.get(OUTPUT_DIRECTORY));
        } catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для документов", e);
        }
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_SUPERVISOR_REVIEW_BACH3_SECS;
    }

    @Override
    public void generate(StudentDto dto) {
        String safeName = dto.fullName().replace(' ', '_');
        String outputFilePath = OUTPUT_DIRECTORY + "/" + safeName + '_' + OUTPUT_FILE_NAME;

        generateDocument(TEMPLATE_PATH, outputFilePath, PLACEHOLDERS, dto);
    }
}
