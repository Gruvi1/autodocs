package ru.nsu.astakhov.autodocs.ui.view.font;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FontType {
    ADWAITA_SANS_REGULAR("/font/AdwaitaSans-Regular.ttf"),
    ADWAITA_SANS_ITALIC("/font/AdwaitaSans-Italic.ttf");

    private final String path;
}
