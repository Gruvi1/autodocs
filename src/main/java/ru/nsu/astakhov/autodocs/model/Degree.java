package ru.nsu.astakhov.autodocs.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Degree {
    BACHELORS("Бакалавриат"),
    MASTERS("Магистратура");

    private final String value;
}
