package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.apache.poi.ss.formula.functions.T;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.EventHandler;
import ru.nsu.astakhov.autodocs.ui.view.CustomComboBox;
import ru.nsu.astakhov.autodocs.ui.view.RoundedButton;

import javax.swing.*;
import java.util.List;

public abstract class Panel extends JPanel {
    private EventHandler eventHandler;

    abstract public void configurePanel();

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

        comboBox.setActionCommand(comboBoxName);
        comboBox.addActionListener(eventHandler);

        return comboBox;
    }

    protected JMenuItem createMenuItem(String itemName) {
        JMenuItem menuItem = new JMenuItem(itemName);

        menuItem.setBorder(BorderFactory.createEmptyBorder());
        menuItem.setActionCommand(itemName);
        menuItem.addActionListener(eventHandler);

        return menuItem;
    }
}
