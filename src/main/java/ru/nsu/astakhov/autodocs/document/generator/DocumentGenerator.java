package ru.nsu.astakhov.autodocs.document.generator;

import ru.nsu.astakhov.autodocs.model.*;

public interface DocumentGenerator {
    void generate(StudentDto dto);
}
