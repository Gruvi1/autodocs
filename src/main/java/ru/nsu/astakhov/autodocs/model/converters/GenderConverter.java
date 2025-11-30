package ru.nsu.astakhov.autodocs.model.converters;

import com.github.petrovich4j.Gender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class GenderConverter implements AttributeConverter<Gender, String> {
    @Override
    public String convertToDatabaseColumn(Gender gender) {
        if (gender == null) {
            return null;
        }

        return gender.name();
    }

    @Override
    public Gender convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }

        return Gender.Male.name().equals(dbData) ? Gender.Male : Gender.Female;
    }
}