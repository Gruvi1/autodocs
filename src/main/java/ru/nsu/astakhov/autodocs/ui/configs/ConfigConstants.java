package ru.nsu.astakhov.autodocs.ui.configs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ConfigConstants {
    APP_NAME("App", "APP_NAME"),
    AUTHOR("App", "AUTHOR"),
    CORPORATION("App", "CORPORATION"),
    VERSION("App", "VERSION"),
    GAP_SMALL("Sizes", "GAP_SMALL"),
    GAP_MEDIUM("Sizes", "GAP_MEDIUM"),
    WINDOW_WIDTH("Sizes", "window_width"),
    WINDOW_HEIGHT("Sizes", "window_height"),
    MENU_SIZE("Sizes", "menu_size"),
    TEXT_SIZE("Sizes", "text_size"),
    TITLE_SIZE("Sizes", "title_size"),
    THEME("UI", "theme"),
    MAXIMIZED("UI", "maximized"),
    SCALE("UI", "scale"),
    PRIMARY_COLOR(PrivateConstants.TAG_COLOR.getValue(), "primary_color"),
    BACKGROUND_COLOR(PrivateConstants.TAG_COLOR.getValue(), "background_color"),
    FOCUS_COLOR(PrivateConstants.TAG_COLOR.getValue(), "focus_color"),
    TEXT_COLOR(PrivateConstants.TAG_COLOR.getValue(), "text_color"),
    ERROR_COLOR(PrivateConstants.TAG_COLOR.getValue(), "error_color");

    private final String section;
    private final String settingName;
}
