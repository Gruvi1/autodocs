package ru.nsu.astakhov.autodocs.ui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.model.*;

@RequiredArgsConstructor
@Getter
public enum GeneratorFilters {
    WORK_TYPE("Тип работы", WorkType.class),
    DEGREE("Степень", Degree.class),
    ACADEMIC_PERIOD("Период обучения", AcademicPeriod.class),
    SPECIALIZATION("Направление", Specialization.class);

    private final String value;
    private final Class<? extends Enum<? extends HasStringValue>> enumClass;
}
