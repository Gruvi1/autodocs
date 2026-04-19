package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class ThesisAssignmentTableProcessor extends TableProcessor {
    public void addCoSupervisor(XWPFTable table) {
        XWPFTableRow row1 = removeAllCells(table.createRow());
        XWPFTableCell row1Cell1 = row1.createCell();
        addTextInCell(row1Cell1, "Соруководитель ВКР");
        row1.createCell();

        XWPFTableRow row2 = removeAllCells(table.createRow());
        XWPFTableCell row2Cell1 = row2.createCell();
        XWPFRun run2 = addTextInCell(row2Cell1, "$(соруководительВКР.должность.работаНГУ)");
        run2.setTextHighlightColor("yellow");
        row2.createCell();

        XWPFTableRow row3 = removeAllCells(table.createRow());
        XWPFTableCell row3Cell1 = row3.createCell();
        XWPFRun run3 = addTextInCell(row3Cell1, "$(соруководительВКР.степень), $(соруководительВКР.звание)");
        run3.setTextHighlightColor("yellow");
        row3.createCell();

        XWPFTableRow row4 = removeAllCells(table.createRow());
        XWPFTableCell row4Cell1 = row4.createCell();
        XWPFRun run4 = addTextInCell(row4Cell1, "$(соруководительвкр.имяКратко)/…………..");
        run4.setTextHighlightColor("yellow");
        row4.createCell();

        XWPFTableRow row5 = removeAllCells(table.createRow());
        XWPFTableCell row5Cell1 = row5.createCell();
        addTextInCell(row5Cell1, "             (ФИО) / (подпись)");
        row5.createCell();

        XWPFTableRow row6 = removeAllCells(table.createRow());
        XWPFTableCell row6Cell1 = row6.createCell();
        addTextInCell(row6Cell1, "$(общаяДатаПодписи)");
        row6.createCell();
    }
}
