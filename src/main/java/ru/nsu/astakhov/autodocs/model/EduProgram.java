package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum EduProgram {
    BACHELOR_PROGRAM("09.03.01 Информатика и вычислительная техника"),
    MASTER_PROGRAM("09.04.01 Информатика и вычислительная техника");

    private final String value;

    public static EduProgram fromValue(String value) {
        for (EduProgram eduProgram : EduProgram.values()) {
            if (eduProgram.value.equals(value)) {
                return eduProgram;
            }
        }
        return null;
//        throw new IllegalArgumentException("Invalid educational program value: " + value);
    }
}
