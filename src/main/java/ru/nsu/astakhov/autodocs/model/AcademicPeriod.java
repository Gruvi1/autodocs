package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AcademicPeriod implements HasStringValue {
    FIRST_COURSE("1 курс"),
    SECOND_COURSE("2 курс"),
    THIRD_COURSE("3 курс"),
    FOURTH_COURSE("4 курс"),
    FIRST_SEMESTER("1 семестр"),
    SECOND_SEMESTER("2 семестр"),
    THIRD_SEMESTER("3 семестр"),
    FOURTH_SEMESTER("4 семестр"),
    FIFTH_SEMESTER("5 семестр"),
    SIXTH_SEMESTER("6 семестр"),
    SEVENTH_SEMESTER("7 семестр"),
    EIGHTH_SEMESTER("8 семестр");

    private final String value;

    @Override
    public String getStringValue() {
        return value;
    }

    public static AcademicPeriod fromValue(String value) {
        for (AcademicPeriod academicPeriod : AcademicPeriod.values()) {
            if (academicPeriod.value.equals(value)) {
                return academicPeriod;
            }
        }
        return null;
    }
}
