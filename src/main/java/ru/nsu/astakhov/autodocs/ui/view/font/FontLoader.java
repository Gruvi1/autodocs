package ru.nsu.astakhov.autodocs.ui.view.font;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.EnumMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FontLoader {
    private static final Map<FontType, Font> cachedFonts = new EnumMap<>(FontType.class);

    public static Font loadFont(FontType type, int size) {
        Font font;
        synchronized (cachedFonts) {
            font = cachedFonts.get(type);
            if (font == null) {
                font = loadAndRegisterFont(type);
                cachedFonts.put(type, font);
            }
        }
        return font.deriveFont(Font.PLAIN, size);
    }

    private static Font loadAndRegisterFont(FontType type) {
        String path = type.getPath();
        try (InputStream inputStream = FontLoader.class.getResourceAsStream(path)) {
            if (inputStream == null) {
                throw new IllegalStateException("Couldn't load font: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);

            return font.deriveFont(Font.PLAIN, 0);
        }
        catch (IOException | FontFormatException e) {
            return new Font("Default", Font.PLAIN, 0);
        }
    }
}
