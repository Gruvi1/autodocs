package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

public class CustomLabel extends JLabel {
    public CustomLabel(String text, int textSize, boolean opaque) {
        super(text);

        configureLabel(textSize, opaque);
    }

    public CustomLabel(String text, boolean opaque) {
        super(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        configureLabel(textSize, opaque);
    }

    public CustomLabel(String text) {
        super(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        configureLabel(textSize,false);
    }

    private void configureLabel(int textSize, boolean opaque) {
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
