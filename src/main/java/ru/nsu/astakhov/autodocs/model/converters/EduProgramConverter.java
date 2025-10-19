package ru.nsu.astakhov.autodocs.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.astakhov.autodocs.model.EduProgram;

@Converter
public class EduProgramConverter implements AttributeConverter<EduProgram, String> {

    @Override
    public String convertToDatabaseColumn(EduProgram eduProgram) {
        if (eduProgram == null) {
            return null;
        }
        return eduProgram.getValue();
    }

    @Override
    public EduProgram convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }
        return EduProgram.fromValue(dbData);
    }
}