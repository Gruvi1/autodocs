package ru.nsu.astakhov.autodocs.document.generator.master.second.aids;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class ApplicationInternshipMast4SemAidsGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Заявление_на_практику_Магистратура_4сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/common/Заявление_на_практику_произ_науч.docx";
    private static final String OUTPUT_DIRECTORY = "document/internship/masters/2nd_course";

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

    public ApplicationInternshipMast4SemAidsGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.APPLICATION_INTERNSHIP_MAST_4SEM_AIDS;
    }
}
