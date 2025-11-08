package ru.nsu.astakhov.autodocs.ui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GeneratorFilters {
    TYPE("Тип"),
    DEGREE("Степень"),
    COURSE("Курс"),
    // TODO: добавить фильтр или убрать параметр
    SPECIALIZATION("Направление");

    private final String value;
}
