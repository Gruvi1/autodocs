package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;

@Getter
public enum Specialization {
    SOFTWARE_ENGINEERING_AND_CS("Программная инженерия и компьютерные науки"),
    CS_AND_SYSTEMS_ENGINEERING("Компьютерные науки и системотехника"),
    AI_AND_DATA_SCIENCE("Искусственный интеллект и Data Science"),
    SOFTWARE_SYSTEMS_DEVELOPMENT("Технология разработки программных систем"),
    INTERNET_OF_THINGS("Интернет вещей");

    private final String value;

    Specialization(String value) {
        this.value = value;
    }

    public static Specialization fromValue(String value) {
        for (Specialization specialization : Specialization.values()) {
            if (specialization.value.equals(value)) {
                return specialization;
            }
        }
        return null;
//        throw new IllegalArgumentException("Invalid specialization value: " + value);
    }
}
