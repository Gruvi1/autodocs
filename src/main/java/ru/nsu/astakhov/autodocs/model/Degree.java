package ru.nsu.astakhov.autodocs.model;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
// TODO: переименовать, потому что пересекается с degree полем у Supervisor
public enum Degree {
    BACHELORS("Бакалавриат"),
    MASTERS("Магистратура");

    private final String value;

    public static Degree fromValue(String value) {
        for (Degree degree : Degree.values()) {
            if (degree.value.equals(value)) {
                return degree;
            }
        }
        return null;
    }
}
