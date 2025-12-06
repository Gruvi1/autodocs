package ru.nsu.astakhov.autodocs.document.generator.bachelor.third;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class InternshipSupervisorReviewBach3CourseSecsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отзыв_руководителя_практики_Бакалавриат_ПИиКН_5сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/3rd_course";

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

    public InternshipSupervisorReviewBach3CourseSecsGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_SUPERVISOR_REVIEW_BACH_3COURSE_SECS;
    }
}
