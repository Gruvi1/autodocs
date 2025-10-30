package ru.nsu.astakhov.autodocs.ui.view.logo;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class LogoLoader {
    public static JLabel loadLogo(LogoType type, int width, int height) {
        JLabel label;
        String path = getPath(type);

        try {
            BufferedImage rawImage =
                    ImageIO.read(Objects.requireNonNull(LogoLoader.class.getResourceAsStream(path)));
            Image scaledImage = rawImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            label = new JLabel(icon);
        }
        catch (RuntimeException | IOException e) {
            label = new JLabel();
        }
        return label;
    }

    public static JLabel loadLogo(LogoType type) {
        JLabel label;
        String path = getPath(type);

        try {
            BufferedImage rawImage =
                    ImageIO.read(Objects.requireNonNull(LogoLoader.class.getResourceAsStream(path)));
            ImageIcon icon = new ImageIcon(rawImage);


            label = new JLabel(icon);
//            label.setBorder(BorderFactory.createEmptyBorder());  // Убираем бордер
//            label.setOpaque(false);
//            label.setHorizontalAlignment(JLabel.CENTER);
//            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            }
        catch (RuntimeException | IOException e) {
            label = new JLabel();
        }
        return label;
    }

    private static String getPath(LogoType type) {
        String theme = ConfigManager.getSetting(ConfigConstants.THEME);

        return String.format(type.getPath(), theme);
    }
}
