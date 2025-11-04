package ru.nsu.astakhov.autodocs.documents;

import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.util.List;

@Service
public class IndWorkBach3Generator extends AbstractDocumentGenerator implements DocumentGenerator{
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

    public IndWorkBach3Generator(RussianWordDecliner decliner) {
        super(decliner);
    }

    @Override
    public void generate(StudentDto dto) {
        String outputFileName = dto.fullName().replace(' ', '_') + OUTPUT_FILE_NAME;
        generateDocument(TEMPLATE_PATH, outputFileName, PLACEHOLDERS, dto);
    }
}