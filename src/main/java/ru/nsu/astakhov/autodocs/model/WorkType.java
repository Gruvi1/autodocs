package ru.nsu.astakhov.autodocs.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum WorkType implements HasStringValue {
    INTERNSHIP("Практика"),
    THESIS("ВКР");

    private final String value;

    @Override
    public String getStringValue() {
        return value;
    }

    public static WorkType fromValue(String value) {
        for (WorkType workType : WorkType.values()) {
            if (workType.value.equals(value)) {
                return workType;
            }
        }
        return null;
    }
}
