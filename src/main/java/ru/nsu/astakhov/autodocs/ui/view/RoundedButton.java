package ru.nsu.astakhov.autodocs.ui.view;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

public class RoundedButton extends JButton {
    private final int rounding;
    public RoundedButton(String text, int rounding) {
        super(text);
        this.rounding = rounding;

        configureButton();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) { // нажал
            Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
            g2.setColor(focusColor);
        }
        else if (getModel().isRollover()) { // навёлся
            Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
            g2.setColor(backgroundColor);
        }
        else { // просто
            Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
            g2.setColor(primaryColor);
        }

        int arcWidth = 10;
        int arcHeight = 10;
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcHeight);
        super.paintComponent(g);
    }

    private void configureButton() {
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        setMargin(new Insets(rounding / 2, rounding, rounding / 2, rounding));
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        setForeground(textColor);

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
}