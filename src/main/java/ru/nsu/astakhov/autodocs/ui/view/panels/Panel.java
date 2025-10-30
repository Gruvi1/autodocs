package ru.nsu.astakhov.autodocs.ui.view.panels;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.RoundedButton;

import javax.swing.*;

public abstract class Panel extends JPanel {
    private final ButtonEventHandler buttonEventHandler;

    public Panel(ButtonEventHandler buttonEventHandler) {
        this.buttonEventHandler = buttonEventHandler;
        configurePanel();
    }

    abstract public void configurePanel();

    JButton createButton(String buttonName) {
        RoundedButton button = new RoundedButton(buttonName);

        button.setActionCommand(buttonName);
        button.addActionListener(buttonEventHandler);

        return button;
    }
}
