package ru.nsu.astakhov.autodocs.model;

public record TableWarning(
        TableType tableType,
        String studentName,
        String fieldName
) {
    @org.jetbrains.annotations.NotNull
    @Override
    public String toString() {
        String template = "В таблице {} у студента {} не определено поле: {}";
        // TODO: возможно добавить параметр в перечисление, чтобы не мудохаться каждый раз
        String type = tableType == TableType.INTERNSHIP ? "практики" : "ВКР";

        return String.format(template, type, studentName, fieldName);
    }
}
