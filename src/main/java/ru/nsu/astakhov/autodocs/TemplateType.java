package ru.nsu.astakhov.autodocs;
//TODO: перенести класс в нужное место (куда?)

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TemplateType {
    // TODO: дублирование тут и в ButtonCommand
    INDIVIDUAL_ASSIGNMENT("Индивидуальное задание"),
    INTERNSHIP_REPORT("Отчёт о практике"),
    INTERNSHIP_SUPERVISOR_REVIEW("Отзыв руководителя практики");


    private final String value;
}
