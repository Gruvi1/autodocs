package ru.nsu.astakhov.autodocs.ui.view.component;

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

    public RoundedButton(String text) {
        super(text);
        this.rounding = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        configureButton();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        if (getModel().isPressed()) {
            Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
            g2.setColor(focusColor);
        }
        else if (getModel().isRollover()) {
            Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
            g2.setColor(backgroundColor);
        }
        else {
            Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
            g2.setColor(primaryColor);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), rounding, rounding);
        super.paintComponent(g);
    }

    private void configureButton() {
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        setMargin(new Insets(smallGap / 2, smallGap, smallGap / 2, smallGap));
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        setForeground(textColor);

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
    }
}