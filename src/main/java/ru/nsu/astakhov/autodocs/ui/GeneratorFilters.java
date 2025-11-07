package ru.nsu.astakhov.autodocs.ui;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum GeneratorFilters {
    TYPE("TypeFilter"),
    DEGREE("DegreeFilter"),
    COURSE("CourseFilter"),
    SPECIALIZATION("SpecializationFilter");

    private final String value;
}
