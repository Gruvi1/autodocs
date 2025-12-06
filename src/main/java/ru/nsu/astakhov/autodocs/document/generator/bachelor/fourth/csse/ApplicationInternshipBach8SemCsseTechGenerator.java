package ru.nsu.astakhov.autodocs.document.generator.bachelor.fourth.csse;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class ApplicationInternshipBach8SemCsseTechGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Заявление_на_практику_Бакалавриат_8сем_техно.docx";
    private static final String TEMPLATE_PATH = "/template/internship/common/Заявление_на_практику_произ_техно.docx";
    private static final String OUTPUT_DIRECTORY = "document/internship/bachelors/4th_course";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(genitiveStudentForm)",
            "$(course)",
            "$(groupName)",
            "$(eduProgram)",
            "$(specialization)",
            "$(genitiveFullName)",
            "$(internshipType)",
            "$(fullPlaceOfInternship)",
            "$(thesisSupervisor.name)",
            "$(thesisSupervisor.position)"
    );

    public ApplicationInternshipBach8SemCsseTechGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.APPLICATION_INTERNSHIP_BACH_8SEM_CSSE_TECH;
    }
}
