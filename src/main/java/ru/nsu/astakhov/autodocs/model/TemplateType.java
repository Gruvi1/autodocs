package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TemplateType {
    INDIVIDUAL_ASSIGNMENT("Индивидуальное задание"),
    INTERNSHIP_REPORT("Отчёт о практике"),
    INTERNSHIP_SUPERVISOR_REVIEW("Отзыв руководителя практики"),
    INTERNSHIP_APPLICATION("Заявление на практику"),
    REVIEW("Отзыв"),
    REVIEWER_COMMENT("Рецензия"),
    THESIS_SUPERVISOR_REVIEW("Отзыв руководителя ВКР");

    private final String value;

    public static TemplateType fromValue(String value) {
        for (TemplateType templateType : TemplateType.values()) {
            if (templateType.value.equals(value)) {
                return templateType;
            }
        }
        return null;
    }
}
