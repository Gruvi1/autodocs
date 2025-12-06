package ru.nsu.astakhov.autodocs.document.generator.master.first.ssd;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.document.generator.AbstractDocumentGenerator;

import java.util.List;

@Service
public class ApplicationInternshipMast2SemSsdGenerator extends AbstractDocumentGenerator {
    private static final String OUTPUT_FILE_NAME = "Заявление_на_практику_Магистратура_2сем.docx";
    private static final String TEMPLATE_PATH = "/template/internship/common/Заявление_на_практику_учеб_ознак.docx";
    private static final String OUTPUT_DIRECTORY = "document/internship/masters/1st_course";

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

    public ApplicationInternshipMast2SemSsdGenerator(RussianWordDecliner decliner) {
        super(decliner, OUTPUT_FILE_NAME, TEMPLATE_PATH, OUTPUT_DIRECTORY, PLACEHOLDERS);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.APPLICATION_INTERNSHIP_MAST_2SEM_SSD;
    }
}