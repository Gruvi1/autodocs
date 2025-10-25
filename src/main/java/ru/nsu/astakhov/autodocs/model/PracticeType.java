package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;

@Getter
public enum PracticeType {
    INTERNSHIP("практика"),
    THESIS("ВКР");

    private final String value;

    PracticeType(String value) {
        this.value = value;
    }
}
