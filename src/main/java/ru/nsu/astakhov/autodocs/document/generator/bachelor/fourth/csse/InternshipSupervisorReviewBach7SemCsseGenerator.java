package ru.nsu.astakhov.autodocs.document.generator.bachelor.fourth.csse;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class InternshipSupervisorReviewBach7SemCsseGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отзыв_руководителя_практики_Бакалавриат_КНиС_7сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/4th_course/csse/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/4th_course/csse";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(internshipType)",
            "$(studentForm)",
            "$(fullName)",
            "$(groupName)",
            "$(course)",
            "$(eduProgram)",
            "$(specialization)",
            "$(fullOrganizationName)",
            "$(organizationName)",
            "$(organizationSupervisor.position)",
            "$(organizationSupervisor.name)"
    );

    public InternshipSupervisorReviewBach7SemCsseGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_SUPERVISOR_REVIEW_BACH_4COURSE_CSSE;
    }
}
