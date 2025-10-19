package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.view.RoundedButton;

import javax.swing.*;

public abstract class Panel extends JPanel {
    public Panel() {
        configurePanel();
    }

    abstract public void configurePanel();

    JButton createButton(String buttonName) {
        RoundedButton button = new RoundedButton(buttonName);

        button.setActionCommand(buttonName);
//        button.addActionListener(buttonEventHandler);

        return button;
    }
}
