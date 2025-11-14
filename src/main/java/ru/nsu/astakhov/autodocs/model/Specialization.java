package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Specialization implements HasStringValue {
    SOFTWARE_ENGINEERING_AND_CS("Программная инженерия и компьютерные науки"),
    CS_AND_SYSTEMS_ENGINEERING("Компьютерные науки и системотехника"),
    AI_AND_DATA_SCIENCE("Искусственный интеллект и Data Science"),
    SOFTWARE_SYSTEMS_DEVELOPMENT("Технология разработки программных систем"),
    INTERNET_OF_THINGS("Интернет вещей");

    private final String value;

    @Override
    public String getStringValue() {
        return value;
    }

    public static Specialization fromValue(String value) {
        for (Specialization specialization : Specialization.values()) {
            if (specialization.value.equals(value)) {
                return specialization;
            }
        }
        return null;
    }
}
