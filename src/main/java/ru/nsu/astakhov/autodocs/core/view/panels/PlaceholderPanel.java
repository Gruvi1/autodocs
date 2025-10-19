package ru.nsu.astakhov.autodocs.core.view.panels;

import ru.nsu.astakhov.autodocs.core.utils.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.core.utils.configs.ConfigManager;

import java.awt.*;

public class PlaceholderPanel extends Panel {
    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);


    }
}
