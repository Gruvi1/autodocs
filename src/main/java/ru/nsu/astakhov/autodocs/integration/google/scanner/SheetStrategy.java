package ru.nsu.astakhov.autodocs.integration.google.scanner;

import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.util.List;

public interface SheetStrategy {
    String buildRange(Course course);
    StudentDto parseRow(List<Object> row, Course course);

    default boolean hasSpecialParsing(Course course) {
        return false;
    }

    default StudentDto parseSpecialRow(List<Object> row, Course course) {
        return parseRow(row, course);
    }
}
