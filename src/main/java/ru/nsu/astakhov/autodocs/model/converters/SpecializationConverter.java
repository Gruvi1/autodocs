package ru.nsu.astakhov.autodocs.model.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.astakhov.autodocs.model.Specialization;

@Converter
public class SpecializationConverter implements AttributeConverter<Specialization, String> {
    @Override
    public String convertToDatabaseColumn(Specialization specialization) {
        if (specialization == null) {
            return null;
        }

        return specialization.getValue();
    }

    @Override
    public Specialization convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        return Specialization.fromValue(dbData);
    }
}
