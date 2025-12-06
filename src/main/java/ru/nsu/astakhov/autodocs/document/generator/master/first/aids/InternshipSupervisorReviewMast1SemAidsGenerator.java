package ru.nsu.astakhov.autodocs.document.generator.master.first.aids;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class InternshipSupervisorReviewMast1SemAidsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отзыв_руководителя_практики_Магистратура_AIiDS_1сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/masters/1st_course/aids/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/masters/1st_course/aids";

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

    public InternshipSupervisorReviewMast1SemAidsGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_SUPERVISOR_REVIEW_MAST_1COURSE_AIDS;
    }
}
