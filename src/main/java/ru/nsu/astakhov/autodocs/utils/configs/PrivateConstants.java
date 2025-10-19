package ru.nsu.astakhov.autodocs.utils.configs;

import lombok.Getter;

@Getter
enum PrivateConstants {
    TAG_COLOR("TAG_COLOR"),
    DARK_COLORS_SECTION("Dark_colors"),
    LIGHT_COLORS_SECTION("Light_colors"),
    DARK_THEME("dark"),
    LIGHT_THEME("light");

    private final String value;

    PrivateConstants(String value) {
        this.value = value;
    }
}
