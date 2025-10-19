package ru.nsu.astakhov.autodocs.utils.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.astakhov.autodocs.core.model.InternshipType;

@Converter
public class InternshipTypeConverter implements AttributeConverter<InternshipType, String> {
    @Override
    public String convertToDatabaseColumn(InternshipType internshipType) {
        if (internshipType == null) {
            return null;
        }

        return internshipType.getValue();
    }

    @Override
    public InternshipType convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isBlank()) {
            return null;
        }

        return InternshipType.fromValue(dbData);
    }
}
