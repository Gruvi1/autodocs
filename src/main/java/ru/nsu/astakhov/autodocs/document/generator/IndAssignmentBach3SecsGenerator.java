package ru.nsu.astakhov.autodocs.document.generator;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import java.util.List;

@Service
public class IndAssignmentBach3SecsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "ИЗ_Бакалавриат_3курс.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/3rd_course";

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
            "$(organizationSupervisor.name)",
            "$(organizationSupervisor.position)"
    );

    public IndAssignmentBach3SecsGenerator(RussianWordDecliner decliner) {
        super(decliner);
    }

    @Override
    protected String getTemplatePath() {
        return TEMPLATE_PATH;
    }

    @Override
    protected String getOutputDirectory() {
        return OUTPUT_DIRECTORY;
    }

    @Override
    protected String getOutputFileName() {
        return OUTPUT_FILE_NAME;
    }

    @Override
    protected List<String> getPlaceholders() {
        return PLACEHOLDERS;
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INDIVIDUAL_ASSIGNMENT_BACH3_SECS;
    }
}