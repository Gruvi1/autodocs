package ru.nsu.astakhov.autodocs.document.generator.bachelor.fourth.secs;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class IndAssignmentBach7SemSecsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "ИЗ_на_практику_Бакалавриат_ПИиКН_7сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/4th_course/secs/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/4th_course/secs";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(genitiveStudentForm)",
            "$(genitiveFullName)",
            "$(eduProgram)",
            "$(specialization)",
            "$(internshipType)",
            "$(fullName)",
            "$(groupName)",
            "$(fullPlaceOfInternship)",
            "$(NSUSupervisor.name)",
            "$(NSUSupervisor.position)",
            "$(thesisSupervisor.name)",
            "$(thesisSupervisor.position)",
            "$(administrativeActFromOrganization)",
            "$(organizationSupervisor.name)",
            "$(organizationSupervisor.position)"
    );

    public IndAssignmentBach7SemSecsGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INDIVIDUAL_ASSIGNMENT_BACH_4COURSE_SECS;
    }
}