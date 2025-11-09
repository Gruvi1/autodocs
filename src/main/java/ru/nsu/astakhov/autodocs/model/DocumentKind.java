package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DocumentKind {
    INDIVIDUAL_ASSIGNMENT("Индивидуальное задание"),
    INTERNSHIP_REPORT("Отчёт о практике"),
    INTERNSHIP_SUPERVISOR_REVIEW("Отзыв руководителя практики");

    private final String value;

    public static DocumentKind fromValue(String value) {
        for (DocumentKind documentKind : DocumentKind.values()) {
            if (documentKind.value.equals(value)) {
                return documentKind;
            }
        }
        return null;
    }
}
