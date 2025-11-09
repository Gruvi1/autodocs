package ru.nsu.astakhov.autodocs.ui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GeneratorFilters {
    WORK_TYPE("Тип работы"),
    DEGREE("Степень"),
    COURSE("Курс"),
    SPECIALIZATION("Направление");

    private final String value;
}
