package ru.nsu.astakhov.autodocs.integration.google.scanner;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ThesisColumnType {
    FULL_NAME(0),
    EMAIL(1),
    PHONE_NUMBER(2),
    GROUP_NAME(3),
    EDU_PROGRAM(4),
    SPECIALIZATION(5),
    ORDER_ON_APPROVAL_TOPIC(6),
    ORDER_ON_CORRECTION_TOPIC(7),
    THESIS_SUPERVISOR_NAME(8),
    THESIS_CO_SUPERVISOR_NAME(9),
    THESIS_CONSULTANT_NAME(10),
    THESIS_TOPIC(11),
    ACTUAL_SUPERVISOR(12),
    REVIEWER(13);

    private final int columnNumber;
}
