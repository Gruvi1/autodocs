package ru.nsu.astakhov.autodocs.ui.view.panels;

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
        RoundedButton button = new RoundedButton(buttonName);

        button.setActionCommand(buttonName);
        button.addActionListener(eventHandler);

        return button;
    }
}
