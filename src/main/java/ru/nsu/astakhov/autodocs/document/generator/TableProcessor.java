package ru.nsu.astakhov.autodocs.document.generator;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class TableProcessor {
    public void addCompetencies(XWPFTable table, Map<String, List<String>> competencies) {
        int globalNumber = 1;
        int columnCount = 6;
        for (String key : competencies.keySet()) {
            globalNumber = addCompetence(table, key, competencies.get(key), globalNumber, columnCount);
        }
        addLastRow(table, columnCount);
    }

    private int addCompetence(
            XWPFTable table,
            String coreCompetence,
            List<String> competencies,
            int globalNumber,
            int columnCount
    ) {

        XWPFTableRow coreRow = table.createRow();
        removeAllCells(coreRow);
        XWPFTableCell coreCell = coreRow.createCell();
        coreCell.getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(columnCount));
        addTextInCell(coreCell, coreCompetence);

        for (String competence : competencies) {
            XWPFTableRow row  = table.createRow();
            removeAllCells(row);
            for (int i = 0; i < columnCount; i++) {
                row.createCell();
            }
            String globalNumberString = globalNumber++ + ".";
            addTextInCell(row.getCell(0), globalNumberString);
            addTextInCell(row.getCell(1), competence);
        }
        return globalNumber;
    }

    private void removeAllCells(XWPFTableRow row) {
        while (!row.getTableCells().isEmpty()) {
            row.removeCell(0);
        }
    }

    private void addTextInCell(XWPFTableCell cell, String text) {
        XWPFRun run = cell.getParagraphs().getFirst().createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(11);
        run.setText(text);
    }

    private void addLastRow(XWPFTable table, int columnCount) {
        String title = "ИТОГОВАЯ ОЦЕНКА";
        String description = "(отлично, хорошо, удовлетворительно, неудовлетворительно)";

        XWPFTableRow lastRow = table.createRow();
        removeAllCells(lastRow);

        XWPFTableCell lastCell = lastRow.createCell();
        lastCell.getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(columnCount - 4));
        lastCell.getParagraphs().getFirst().setAlignment(ParagraphAlignment.CENTER);
        lastCell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        XWPFRun lastRun = lastCell.getParagraphs().getFirst().createRun();
        lastRun.setFontFamily("Times New Roman");
        lastRun.setFontSize(11);
        lastRun.setText(title);
        lastRun.setBold(true);
        lastRun.addBreak();

        XWPFRun lastRun2 = lastCell.getParagraphs().getFirst().createRun();
        lastRun2.setFontFamily("Times New Roman");
        lastRun2.setFontSize(11);
        lastRun2.setText(description);


        XWPFTableCell lastCell2 = lastRow.createCell();
        lastCell2.getCTTc().addNewTcPr().addNewGridSpan().setVal(BigInteger.valueOf(columnCount - 2));
        lastCell2.getParagraphs().getFirst().setAlignment(ParagraphAlignment.CENTER);
        lastCell2.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        addTextInCell(lastCell2, "Отлично");
    }

    public void removeMarkerRow(XWPFTable table, String marker) {
        // Идём с конца к началу, чтобы индексы не смещались
        for (int i = table.getNumberOfRows() - 1; i >= 0; i--) {
            XWPFTableRow row = table.getRow(i);

            // Проверяем все ячейки в строке
            for (XWPFTableCell cell : row.getTableCells()) {
                String cellText = cell.getText();
                if (cellText != null && cellText.contains(marker)) {
                    // Нашли маркер - удаляем всю строку
                    table.removeRow(i);
                    break; // Выходим из цикла по ячейкам, строка уже удалена
                }
            }
        }
    }
}
