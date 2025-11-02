package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.EventHandler;
import ru.nsu.astakhov.autodocs.ui.view.RoundedButton;

import javax.swing.*;

public abstract class Panel extends JPanel {
    private EventHandler eventHandler;

    abstract public void configurePanel();

    protected void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    JButton createButton(String buttonName) {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        RoundedButton button = new RoundedButton(buttonName, smallGap);

        button.setActionCommand(buttonName);
        button.addActionListener(eventHandler);

        return button;
    }
}
