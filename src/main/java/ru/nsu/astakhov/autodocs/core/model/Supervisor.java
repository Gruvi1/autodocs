package ru.nsu.astakhov.autodocs.core.model;

import jakarta.persistence.Embeddable;

@Embeddable
public record Supervisor (
        String name, // фио
        String position, // должность
        String degree, // учёная степень
        String title // учёное звание
) {
}