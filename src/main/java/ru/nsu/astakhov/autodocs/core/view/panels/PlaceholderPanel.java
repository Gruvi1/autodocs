package ru.nsu.astakhov.autodocs.core.view.panels;

import ru.nsu.astakhov.autodocs.core.utils.ConfigConstants;
import ru.nsu.astakhov.autodocs.core.utils.ConfigManager;

import java.awt.*;

public class PlaceholderPanel extends Panel {
    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);


    }
}
