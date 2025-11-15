package ru.nsu.astakhov.autodocs.document.generator;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import java.util.List;

@Service
public class InternshipReportBach3SecsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отчёт_о_практике_Бакалавриат_3курс.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/3rd_course";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(eduProgram)",
            "$(specialization)",
            "$(internshipType)",
            "$(genitiveStudentForm)",
            "$(genitiveFullName)",
            "$(groupName)",
            "$(fullPlaceOfInternship)",
            "$(organizationSupervisor.name)",
            "$(organizationSupervisor.position)",
            "$(NSUSupervisor.name)",
            "$(NSUSupervisor.position)",
            "$(thesisSupervisor.name)",
            "$(thesisSupervisor.position)"
    );


    public InternshipReportBach3SecsGenerator(RussianWordDecliner decliner) {
        super(decliner);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_REPORT_BACH3_SECS;
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
}
