package ru.nsu.astakhov.autodocs.ui.view;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DegreeFilter {
    BACHELORS("Бакалавриат"),
    MASTERS("Магистратура");

    private final String value;
}
