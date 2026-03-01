package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Specialization implements HasStringValue {
    SOFTWARE_ENGINEERING_AND_CS("Программная инженерия и компьютерные науки", "пиикн"),
    CS_AND_SYSTEMS_ENGINEERING("Компьютерные науки и системотехника", "книс"),
    AI_AND_DATA_SCIENCE("Искусственный интеллект и Data Science", "aiids"),
    SOFTWARE_SYSTEMS_DEVELOPMENT("Технология разработки программных систем", "трпс"),
    INTERNET_OF_THINGS("Интернет вещей", "iot");

    private final String value;
    private final String abbreviation;

    @Override
    public String getStringValue() {
        return value;
    }

    public static Specialization fromValue(String value) {
        for (Specialization s : Specialization.values()) {
            if (s.value.equals(value) || (s.abbreviation != null && s.abbreviation.equals(value))) {
                return s;
            }
        }
        return null;
    }
}
