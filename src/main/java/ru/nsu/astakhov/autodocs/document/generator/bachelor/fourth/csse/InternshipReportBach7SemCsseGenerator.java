package ru.nsu.astakhov.autodocs.document.generator.bachelor.fourth.csse;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class InternshipReportBach7SemCsseGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отчет_о_практике_Бакалавриат_КНиС_7сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/4th_course/csse/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/4th_course/csse";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(eduProgram)",
            "$(specialization)",
            "$(internshipType)",
            "$(genitiveStudentForm)",
            "$(genitiveFullName)",
            "$(groupName)",
            "$(course)",
            "$(fullPlaceOfInternship)",
            "$(organizationSupervisor.name)",
            "$(organizationSupervisor.position)",
            "$(NSUSupervisor.name)",
            "$(NSUSupervisor.position)",
            "$(thesisSupervisor.name)",
            "$(thesisSupervisor.position)"
    );

    public InternshipReportBach7SemCsseGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_REPORT_BACH_4COURSE_CSSE;
    }
}
