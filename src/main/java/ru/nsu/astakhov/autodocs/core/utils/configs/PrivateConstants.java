package ru.nsu.astakhov.autodocs.core.utils.configs;

enum PrivateConstants {
    TAG_COLOR("TAG_COLOR"),
    DARK_COLORS_SECTION("Dark_colors"),
    LIGHT_COLORS_SECTION("Light_colors"),
    DARK_THEME("dark"),
    LIGHT_THEME("light");

    private final String constant;

    PrivateConstants(String constant) {
        this.constant = constant;
    }

    String getString() {
        return constant;
    }
}
