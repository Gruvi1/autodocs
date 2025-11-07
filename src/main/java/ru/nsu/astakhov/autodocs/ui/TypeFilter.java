package ru.nsu.astakhov.autodocs.ui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TypeFilter {
    INTERNSHIP("Практика"),
    THESIS("ВКР");

    private final String value;
}
