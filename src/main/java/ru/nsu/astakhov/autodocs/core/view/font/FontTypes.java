package ru.nsu.astakhov.autodocs.core.view.font;

public enum FontTypes {
    ADWAITA_SANS_REGULAR("/fonts/AdwaitaSans-Regular.ttf"),
    ADWAITA_SANS_ITALIC("/fonts/AdwaitaSans-Italic.ttf");

    private final String path;

    FontTypes(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
