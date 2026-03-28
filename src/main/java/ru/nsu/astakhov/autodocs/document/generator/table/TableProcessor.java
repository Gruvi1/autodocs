package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public abstract class TableProcessor {

    protected void removeAllCells(XWPFTableRow row) {
        while (!row.getTableCells().isEmpty()) {
            row.removeCell(0);
        }
    }

    protected XWPFRun addTextInCell(XWPFTableCell cell, String text) {
        XWPFRun run = cell.getParagraphs().getFirst().createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(11);
        run.setText(text);
        return run;
    }

    public void removeMarkerRow(XWPFTable table, String marker) {
        for (int i = table.getNumberOfRows() - 1; i >= 0; i--) {
            XWPFTableRow row = table.getRow(i);

            for (XWPFTableCell cell : row.getTableCells()) {
                String cellText = cell.getText();
                if (cellText != null && cellText.contains(marker)) {
                    table.removeRow(i);
                    break;
                }
            }
        }
    }
}
