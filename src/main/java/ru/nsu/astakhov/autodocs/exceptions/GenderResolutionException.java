package ru.nsu.astakhov.autodocs.exceptions;

import lombok.Getter;
import ru.nsu.astakhov.autodocs.model.StudentDto;

@Getter
public class GenderResolutionException extends RuntimeException {
    private final StudentDto student;

    public GenderResolutionException(StudentDto student) {
        super("Не удалось определить пол для студента: " + student.fullName());
        this.student = student;
    }
}
