package ru.nsu.astakhov.autodocs.ui.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
enum PrivateConstants {
    TAG_COLOR("TAG_COLOR"),
    DARK_COLORS_SECTION("Dark_colors"),
    LIGHT_COLORS_SECTION("Light_colors"),
    DARK_THEME("dark"),
    LIGHT_THEME("light");

    private final String value;
}
