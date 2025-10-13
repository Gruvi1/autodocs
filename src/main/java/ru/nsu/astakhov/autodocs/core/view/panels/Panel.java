package ru.nsu.astakhov.autodocs.core.view.panels;

import ru.nsu.astakhov.autodocs.core.view.RoundedButton;

import javax.swing.*;
import java.awt.*;

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
