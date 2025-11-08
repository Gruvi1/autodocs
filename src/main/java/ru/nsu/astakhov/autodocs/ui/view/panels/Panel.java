package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.EventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomComboBox;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;

import javax.swing.*;

public abstract class Panel extends JPanel {
    private EventHandler eventHandler;

    public abstract void configurePanel();

    protected void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    protected JButton createButton(String buttonName) {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        RoundedButton button = new RoundedButton(buttonName, smallGap);

        button.setActionCommand(buttonName);
        button.addActionListener(eventHandler);

        return button;
    }

    protected JComboBox<String> createComboBox(String comboBoxName, String[] parameters) {
        CustomComboBox comboBox = new CustomComboBox(parameters);
        int a = 5;
        comboBox.setActionCommand(comboBoxName);
        comboBox.addActionListener(eventHandler);

        return comboBox;
    }
}
