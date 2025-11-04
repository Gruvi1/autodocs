package ru.nsu.astakhov.autodocs.documents;

import ru.nsu.astakhov.autodocs.model.StudentDto;

public interface DocumentGenerator {
    void generate(StudentDto dto);
}
