package ru.nsu.astakhov.autodocs.core.repositories;

import lombok.Getter;

@Getter
public enum InternshipType {
    ACADEMIC_RESEARCH("Учебная практика (научно-исследовательская работа (получение первичных навыков научно-исследовательской работы))"),
    ACADEMIC_ORIENTATION("Учебная практика (ознакомительная практика)"),
    ACADEMIC_TECHNICAL("Учебная практика (технологическая (проектно-технологическая) практика)"),
    INDUSTRIAL_RESEARCH("Производственная практика (научно-исследовательская работа)"),
    INDUSTRIAL_TECHNICAL("Производственная практика (технологическая (проектно-технологическая) практика)");


    private final String value;

    InternshipType(String value) {
        this.value = value;
    }

    public static InternshipType fromValue(String value) {
        for (InternshipType internshipType : InternshipType.values()) {
            if (internshipType.value.equals(value)) {
                return internshipType;
            }
        }
        throw new IllegalArgumentException("Invalid internship type value: " + value);
    }
}
