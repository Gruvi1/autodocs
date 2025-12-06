package ru.nsu.astakhov.autodocs.document.generator.bachelor.third;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class InternshipReportBach3CourseSecsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Отчет_о_практике_Бакалавриат_ПИиКН_5сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/" + OUTPUT_FILE_NAME;
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/3rd_course";

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

    public InternshipReportBach3CourseSecsGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INTERNSHIP_REPORT_BACH_3COURSE_SECS;
    }
}
