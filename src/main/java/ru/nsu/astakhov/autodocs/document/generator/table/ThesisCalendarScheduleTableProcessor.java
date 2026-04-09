package ru.nsu.astakhov.autodocs.document.generator.table;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.util.List;

public class ThesisCalendarScheduleTableProcessor extends TableProcessor {
    public void addListTopic(XWPFTable table, List<String> topics) {
        int globalNumber = 8;
        for (String topic : topics) {
            globalNumber = addTopic(table, topic, globalNumber);
        }
    }

    private int addTopic(XWPFTable table, String topic, int globalNumber) {
        XWPFTableRow row = removeAllCells(table.createRow());

        XWPFTableCell cell1 = row.createCell();
        addTextInCell(cell1, String.valueOf(globalNumber));
        setCellBorders(cell1);
        XWPFParagraph paragraph1 = cell1.getParagraphs().getFirst();
        paragraph1.setAlignment(ParagraphAlignment.CENTER);
        cell1.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        removeIndent(paragraph1);

        XWPFTableCell cell2 = row.createCell();
        addTextInCell(cell2, topic);
        setCellBorders(cell2);
        removeIndent(cell2.getParagraphs().getFirst());

        setCellBorders(row.createCell());
        setCellBorders(row.createCell());

        return ++globalNumber;
    }
}
