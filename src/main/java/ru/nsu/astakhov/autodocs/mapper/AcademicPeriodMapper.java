package ru.nsu.astakhov.autodocs.mapper;

import ru.nsu.astakhov.autodocs.model.AcademicPeriod;
import ru.nsu.astakhov.autodocs.model.Course;

public class AcademicPeriodMapper {
    public static Course getCourseFromAcademicPeriod(AcademicPeriod academicPeriod) {
        return switch (academicPeriod) {
            case FIRST_COURSE, FIRST_SEMESTER, SECOND_SEMESTER -> Course.FIRST;
            case SECOND_COURSE, THIRD_SEMESTER, FOURTH_SEMESTER -> Course.SECOND;
            case THIRD_COURSE, FIFTH_SEMESTER, SIXTH_SEMESTER -> Course.THIRD;
            case FOURTH_COURSE, SEVENTH_SEMESTER, EIGHTH_SEMESTER -> Course.FOURTH;
        };
    }
}
