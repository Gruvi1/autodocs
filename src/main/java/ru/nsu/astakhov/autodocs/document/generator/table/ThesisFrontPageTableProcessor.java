package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ThesisFrontPageTableProcessor extends TableProcessor {
    public void addCoSupervisor(XWPFTable table) {
        XWPFTableRow row1 = removeAllCells(table.createRow());
        row1.createCell();
        XWPFTableCell row1Cell2 = row1.createCell();
        addTextInCell(row1Cell2, "Соруководитель ВКР");

        XWPFTableRow row2 = removeAllCells(table.createRow());
        row2.createCell();
        XWPFTableCell row2Cell2 = row2.createCell();
        XWPFRun run2 = addTextInCell(row2Cell2, "$(соруководительВКР.степень) $(соруководительВКР.звание)");
        run2.setTextHighlightColor("yellow");

        XWPFTableRow row3 = removeAllCells(table.createRow());
        row3.createCell();
        XWPFTableCell row3Cell2 = row3.createCell();
        XWPFRun run3 = addTextInCell(row3Cell2, "$(соруководительВКР.должность.работаНГУ)");
        run3.setTextHighlightColor("yellow");

        XWPFTableRow row4 = removeAllCells(table.createRow());
        row4.createCell();
        XWPFTableCell row4Cell2 = row4.createCell();
        XWPFRun run4 = addTextInCell(row4Cell2, "$(соруководительвкр.имяКратко)/…………..");
        run4.setTextHighlightColor("yellow");

        XWPFTableRow row5 = removeAllCells(table.createRow());
        row5.createCell();
        XWPFTableCell row5Cell2 = row5.createCell();
        addTextInCell(row5Cell2, "             (ФИО) / (подпись)");

        XWPFTableRow row6 = removeAllCells(table.createRow());
        row6.createCell();
        XWPFTableCell row6Cell2 = row6.createCell();
        addTextInCell(row6Cell2, "«……» мая 2026 г.");
    }
}
