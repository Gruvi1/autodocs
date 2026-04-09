package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

import java.util.List;
import java.util.Map;

public class ThesisSupervisorReviewTableProcessor extends TableProcessor {
    public void addCompetencies(XWPFTable table, Map<String, List<String>> competencies) {
        for (String key : competencies.keySet()) {
            addCompetence(table, key, competencies.get(key));
        }
    }

    private void addCompetence(
            XWPFTable table,
            String coreCompetence,
            List<String> competencies
    ) {

        XWPFTableRow coreRow = table.createRow();
        removeAllCells(coreRow);
        XWPFTableCell coreCell = coreRow.createCell();
        XWPFTableCell coreCell2 = coreRow.createCell();
        XWPFRun run = addTextInCell(coreCell, coreCompetence);
        run.setBold(true);


        CTTc cell1 = coreCell2.getCTTc();
        CTTcPr tcPr1 = cell1.isSetTcPr() ? cell1.getTcPr() : cell1.addNewTcPr();
        tcPr1.addNewVMerge().setVal(STMerge.RESTART);


        for (String competence : competencies) {
            XWPFTableRow row  = table.createRow();
            removeAllCells(row);
            addTextInCell(row.createCell(), competence);
            CTTc cell2 = row.createCell().getCTTc();
            CTTcPr tcPr2 = cell2.isSetTcPr() ? cell2.getTcPr() : cell2.addNewTcPr();
            tcPr2.addNewVMerge().setVal(STMerge.CONTINUE);
        }
    }
}
