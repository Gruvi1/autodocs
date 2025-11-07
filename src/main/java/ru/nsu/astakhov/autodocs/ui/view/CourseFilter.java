package ru.nsu.astakhov.autodocs.ui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum CourseFilter {
    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4");

    private final String value;
}
