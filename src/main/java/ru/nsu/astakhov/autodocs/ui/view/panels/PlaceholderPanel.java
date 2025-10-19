package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.utils.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.utils.configs.ConfigManager;

import java.awt.*;

public class PlaceholderPanel extends Panel {
    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);


    }
}
