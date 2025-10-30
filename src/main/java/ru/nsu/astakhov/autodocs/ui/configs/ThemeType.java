package ru.nsu.astakhov.autodocs.ui.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ThemeType {
    DARK("dark"),
    LIGHT("light");

    private final String value;
}
