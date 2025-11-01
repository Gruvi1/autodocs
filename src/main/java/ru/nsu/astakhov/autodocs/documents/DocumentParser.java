package ru.nsu.astakhov.autodocs.documents;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.Map.entry;

@Slf4j
@RequiredArgsConstructor
@Service
public class DocumentParser {
    private Map<String, Function<StudentDto, String>> resolvers = Map.ofEntries(
            entry("$(fullName)", StudentDto::fullName),
            entry("$(course)", tempDto -> String.valueOf(tempDto.course().getValue())),
            entry("$(email)", StudentDto::email),
            entry("$(phoneNumber)", StudentDto::phoneNumber),
            entry("$(eduProgram)", tempDto -> tempDto.eduProgram().getValue()),
            entry("$(groupName)", StudentDto::groupName),
            entry("$(specialization)", tempDto -> tempDto.specialization().getValue()),
            entry("$(orderOnApprovalTopic)", StudentDto::orderOnApprovalTopic),
            entry("$(orderOnCorrectionTopic)", StudentDto::orderOnCorrectionTopic),
            entry("$(actualSupervisor)", StudentDto::actualSupervisor),
            entry("$(thesisCoSupervisor)", StudentDto::thesisCoSupervisor),
            entry("$(thesisConsultant)", StudentDto::thesisConsultant),
            entry("$(thesisTopic)", StudentDto::thesisTopic),
            entry("$(reviewer)", StudentDto::reviewer),
            entry("$(internshipType)", tempDto -> tempDto.internshipType().getValue()),
            entry("$(thesisSupervisor.name)", tempDto -> tempDto.thesisSupervisor().name()),
            entry("$(thesisSupervisor.position)", tempDto -> tempDto.thesisSupervisor().position()),
            entry("$(thesisSupervisor.degree)", tempDto -> tempDto.thesisSupervisor().degree()),
            entry("$(thesisSupervisor.title)", tempDto -> tempDto.thesisSupervisor().title()),
            entry("$(fullOrganizationName)", StudentDto::fullOrganizationName),
            entry("$(NSUSupervisor.name)", tempDto -> tempDto.NSUSupervisor().name()),
            entry("$(NSUSupervisor.position)", tempDto -> tempDto.NSUSupervisor().position()),
            entry("$(NSUSupervisor.degree)", tempDto -> tempDto.NSUSupervisor().degree()),
            entry("$(NSUSupervisor.title)", tempDto -> tempDto.NSUSupervisor().title()),
            entry("$(organizationSupervisor.name)", tempDto -> tempDto.organizationSupervisor().name()),
            entry("$(organizationSupervisor.position)", tempDto -> tempDto.organizationSupervisor().position()),
            entry("$(organizationSupervisor.degree)", tempDto -> tempDto.organizationSupervisor().degree()),
            entry("$(organizationSupervisor.title)", tempDto -> tempDto.organizationSupervisor().title()),
            entry("$(administrativeActFromOrganization)", StudentDto::administrativeActFromOrganization),
            entry("$(fullPlaceOfInternship)", StudentDto::fullPlaceOfInternship),
            entry("$(organizationName)", StudentDto::organizationName)
    );
    public void createIndWorkDocBach3(StudentDto dto) {
        String filePath = "/бак_3_ИЗ.docx";
        String newFilePath = "new_practice_doc.docx";


        List<String> indWorkBach3Placeholders = List.of(
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
                "$(organizationSupervisor.position)");


        try (InputStream in = DocumentParser.class.getResourceAsStream(filePath);
             XWPFDocument newDoc = new XWPFDocument(in);
             FileOutputStream out = new FileOutputStream(newFilePath)) {

            for (XWPFParagraph p : newDoc.getParagraphs()) {
                String text = p.getText();
                if (containList(text, indWorkBach3Placeholders)) {
                    processParagraph(p, indWorkBach3Placeholders, dto);
                }
            }

            newDoc.write(out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void processParagraph(XWPFParagraph p, List<String> list, StudentDto dto) {
        for (XWPFRun run : p.getRuns()) {
            String allRunText = run.text();

            if (allRunText == null) {
                continue;
            }

            String replaceable;
            while ((replaceable = getFirstReplaceFromList(allRunText, list)) != null) {

                String fromDto = resolvers.get(replaceable).apply(dto);
                allRunText = allRunText.replace(replaceable, fromDto);
            }
            run.getCTR().setTArray(new CTText[0]);
            run.setText(allRunText);
        }
    }

    private boolean containList(String text, List<String> list) {
        for (String value : list) {
            if (text.contains(value)) {
                return true;
            }
        }
        return false;
    }

    private String getFirstReplaceFromList(String text, List<String> list) {
        for (String value : list) {
            if (text.contains(value)) {
                return value;
            }
        }

        return null;
    }

}
