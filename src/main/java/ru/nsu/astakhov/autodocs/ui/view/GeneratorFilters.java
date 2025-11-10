package ru.nsu.astakhov.autodocs.ui.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.model.WorkType;
import ru.nsu.astakhov.autodocs.ui.view.panels.HasStringValue;

@RequiredArgsConstructor
@Getter
public enum GeneratorFilters {
    WORK_TYPE("Тип работы", WorkType.class),
    DEGREE("Степень", Degree.class),
    COURSE("Курс", Course.class),
    SPECIALIZATION("Направление", Specialization.class);

    private final String value;
    private final Class<? extends Enum<? extends HasStringValue>> enumClass;
}
