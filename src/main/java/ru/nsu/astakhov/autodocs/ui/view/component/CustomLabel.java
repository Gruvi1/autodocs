package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String text, boolean opaque, int textSize) {
        super(text);

        configureLabel(opaque, textSize);
    }

    public CustomLabel(String text, boolean opaque) {
        super(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        configureLabel(opaque, textSize);
    }

    public CustomLabel(String text) {
        super(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        configureLabel(false, textSize);
    }

    private void configureLabel(boolean opaque, int textSize) {
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        setBackground(backgroundColor);
        setForeground(textColor);
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        setOpaque(opaque);
        if (opaque) {
            setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));
        }
    }
}
