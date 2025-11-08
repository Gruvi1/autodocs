package ru.nsu.astakhov.autodocs.ui.view.logo;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogoLoader {
    public static JLabel loadLogo(LogoType type, int width, int height) {
        return createLogo(type, width, height);
    }

    public static JLabel loadLogo(LogoType type) {
        return createLogo(type, null, null);
    }

    private static JLabel createLogo(LogoType type, Integer width, Integer height) {
        JLabel label;
        String path = getPath(type);

        try {
            BufferedImage rawImage =
                    ImageIO.read(Objects.requireNonNull(LogoLoader.class.getResourceAsStream(path)));

            width = width == null ? rawImage.getWidth() : width;
            height = height == null ? rawImage.getHeight() : height;

            Image scaledImage = rawImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            label = new JLabel(icon);
        }
        catch (IOException e) {
            label = new JLabel();
        }
        return label;
    }

    private static String getPath(LogoType type) {
        String theme = ConfigManager.getSetting(ConfigConstants.THEME);

        return String.format(type.getPath(), theme);
    }
}
