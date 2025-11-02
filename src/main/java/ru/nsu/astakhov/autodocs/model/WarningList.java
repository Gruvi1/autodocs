package ru.nsu.astakhov.autodocs.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WarningList {
    private final List<TableWarning> warnings;

    public WarningList() {
        warnings = new ArrayList<>();
    }

    public void addWarning(TableType tableType, String student, String fieldName) {
        warnings.add(new TableWarning(tableType, student, fieldName));
    }

    public List<TableWarning> getWarnings() {
        return new ArrayList<>(warnings);
    }

    public void clear() {
        warnings.clear();
    }
}
