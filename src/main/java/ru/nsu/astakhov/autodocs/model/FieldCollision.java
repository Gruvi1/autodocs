package ru.nsu.astakhov.autodocs.model;

import java.util.function.Consumer;

public record FieldCollision(
        StudentEntity entity,
        String studentName,
        String fieldName,
        Consumer<String> resolver,
        String practiceValue,
        String thesisValue
) {
    public void resolve(String value) {
        resolver.accept(value);
    }
}
