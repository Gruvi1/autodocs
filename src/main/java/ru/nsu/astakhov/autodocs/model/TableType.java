package ru.nsu.astakhov.autodocs.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum TableType {
    INTERNSHIP("Практика"),
    THESIS("ВКР");

    private final String value;
}
