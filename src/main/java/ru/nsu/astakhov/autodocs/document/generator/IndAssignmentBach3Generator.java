package ru.nsu.astakhov.autodocs.document.generator;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.document.RussianWordDecliner;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.util.List;

@Service
public class IndAssignmentBach3Generator extends AbstractDocumentGenerator {
    private static final String TEMPLATE_PATH = "/template/internship/bachelors/3rd_course/ИЗ_Бакалавриат_3курс.docx";
    private static final String OUTPUT_FILE_NAME = "_ИЗ_Бакалавриат_3курс.docx";

    private static final List<String> PLACEHOLDERS = List.of(
            "$(studentForm)",
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

    public IndAssignmentBach3Generator(RussianWordDecliner decliner) {
        super(decliner);
    }

    @Override
    public GeneratorType getType() {
        return GeneratorType.INDIVIDUAL_ASSIGNMENT_BACH3_SECS;
    }

    @Override
    public void generate(StudentDto dto) {
        String outputFileName = dto.fullName().replace(' ', '_') + OUTPUT_FILE_NAME;
        generateDocument(TEMPLATE_PATH, outputFileName, PLACEHOLDERS, dto);
    }
}