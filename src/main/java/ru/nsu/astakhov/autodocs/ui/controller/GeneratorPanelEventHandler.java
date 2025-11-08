package ru.nsu.astakhov.autodocs.ui.controller;

import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class GeneratorPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final GeneratorPanel panel;
    private final Runnable action;

    public GeneratorPanelEventHandler(Controller controller, GeneratorPanel panel, Runnable action) {
        this.controller = controller;
        this.panel = panel;
        this.action = action;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JComboBox<?>) {
            action.run();
        }
    }
}
