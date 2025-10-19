package ru.nsu.astakhov.autodocs.core.utils.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import ru.nsu.astakhov.autodocs.core.repositories.Course;

@Converter
public class CourseConverter implements AttributeConverter<Course, Integer> {
    @Override
    public Integer convertToDatabaseColumn(Course course) {
        if (course == null) {
            return null;
        }

        return course.getValue();
    }

    @Override
    public Course convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }

        return Course.fromValue(dbData);
    }
}
