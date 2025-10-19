package ru.nsu.astakhov.autodocs.core.model;

import lombok.Getter;

@Getter
public enum Course {
    FIRST(1),
    SECOND(2),
    THIRD(3),
    FOURTH(4),
    FIFTH(5);

    private final int value;

    Course(int value) {
        this.value = value;
    }

    public static Course fromValue(int value) {
        for (Course course : Course.values()) {
            if (course.value == value) {
                return course;
            }
        }
        throw new IllegalArgumentException("Invalid course value: " + value);
    }
}