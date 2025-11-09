package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InternshipType {
    ACADEMIC_RESEARCH("Учебная практика (научно-исследовательская работа (получение первичных навыков научно-исследовательской работы))"),
    ACADEMIC_ORIENTATION("Учебная практика (ознакомительная практика)"),
    ACADEMIC_TECHNICAL("Учебная практика (технологическая (проектно-технологическая) практика)"),
    INDUSTRIAL_RESEARCH("Производственная практика (научно-исследовательская работа)"),
    INDUSTRIAL_TECHNICAL("Производственная практика (технологическая (проектно-технологическая) практика)");

    private final String value;

    public static InternshipType fromValue(String value) {
        for (InternshipType internshipType : InternshipType.values()) {
            if (internshipType.value.equals(value)) {
                return internshipType;
            }
        }
        return null;
    }
}
