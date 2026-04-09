package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTInd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTc;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;

import java.math.BigInteger;

public abstract class TableProcessor {
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

    protected XWPFTableRow removeAllCells(XWPFTableRow row) {
        while (!row.getTableCells().isEmpty()) {
            row.removeCell(0);
        }
        return row;
    }

    protected XWPFRun addTextInCell(XWPFTableCell cell, String text) {
        XWPFRun run = cell.getParagraphs().getFirst().createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(11);
        run.setText(text);
        return run;
    }

    protected void removeIndent(XWPFParagraph paragraph) {
        CTP ctp = paragraph.getCTP();
        CTPPr ppr = ctp.getPPr();
        if (ppr == null) ppr = ctp.addNewPPr();

        CTInd ind = ppr.getInd();
        if (ind == null) ind = ppr.addNewInd();

        ind.setFirstLine(BigInteger.valueOf(0));
        ind.setHanging(BigInteger.valueOf(0));
        ind.setLeft(BigInteger.valueOf(0));
    }

    protected void setCellBorders(XWPFTableCell cell) {
        CTTc ctTc = cell.getCTTc();
        CTTcPr tcPr = ctTc.getTcPr();
        if (tcPr == null) tcPr = ctTc.addNewTcPr();

        CTTcBorders borders = tcPr.addNewTcBorders();
        CTBorder border = borders.addNewTop();
        border.setVal(STBorder.SINGLE);
        border.setColor("auto");
        border.setSz(BigInteger.valueOf(4));
        border.setSpace(BigInteger.valueOf(0));

        borders.setLeft(border);
        borders.setBottom(border);
        borders.setRight(border);
    }
}
