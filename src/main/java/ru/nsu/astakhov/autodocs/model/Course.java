package ru.nsu.astakhov.autodocs.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Course {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4);

    private final int value;

    public static Course fromValue(int value) {
        for (Course course : Course.values()) {
            if (course.value == value) {
                return course;
            }
        }
        return null;
    }
}