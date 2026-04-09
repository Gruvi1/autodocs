package ru.nsu.astakhov.autodocs.integration.google.scanner;

import java.util.List;

public class SheetUtil {
    public static int findLastRowIndex(List<List<Object>> rows) {
        int size = rows.size();
        if (size < 2) {
            return 1;
        }
        for (int i = size - 1; i != 1; --i) {
            if (rows.get(i).isEmpty()) {
                return i;
            }
        }
        return rows.size();
    }

    public static String readCellAsString(List<Object> row, int index) {
        if (index >= row.size() || row.get(index) == null) {
            return "";
        }
        return row.get(index).toString().trim();
    }
}
