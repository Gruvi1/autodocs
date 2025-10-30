package ru.nsu.astakhov.autodocs.ui.view.font;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class FontLoader {
    public static Font loadFont(FontType type, int size) {
        String path = type.getPath();
        try (InputStream inputStream = FontLoader.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new RuntimeException("Couldn't load font: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font.deriveFont(Font.PLAIN, size);
        }
        catch (IOException | FontFormatException e) {
            return new Font("Default", Font.PLAIN, size);
        }
    }
}
