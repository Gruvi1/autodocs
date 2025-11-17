package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.handler.EventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomComboBox;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;

import javax.swing.*;
import java.awt.*;

public abstract class Panel extends JPanel implements Listener {
    protected final int smallGap;
    protected final int mediumGap;

    protected final int menuTextSize;
    protected final int textSize;
    protected final int titleTextSize;

    protected final Color primaryColor;
    protected final Color backgroundColor;
    protected final Color focusColor;
    protected final Color textColor;


    protected Panel() {
        this.smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        this.mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        this.menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        this.textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));
        this.titleTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TITLE_SIZE));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        this.textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
    }

    private transient EventHandler eventHandler;

    protected abstract void configurePanel();

    protected void setEventHandler(EventHandler eventHandler) {
        this.eventHandler = eventHandler;
    }

    protected JButton createButton(String buttonName) {
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
}
