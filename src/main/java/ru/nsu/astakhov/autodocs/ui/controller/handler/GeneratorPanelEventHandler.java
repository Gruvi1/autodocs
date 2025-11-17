package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.StudentListPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

@RequiredArgsConstructor
public class GeneratorPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final GeneratorPanel panel;
    private final Runnable action;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JComboBox<?>) {
            action.run();
        }
        else if (source instanceof JButton) {
            String command = e.getActionCommand();
            ButtonCommand buttonCommand = ButtonCommand.fromString(command);

            if (buttonCommand == ButtonCommand.SELECT_STUDENTS) {
                List<GeneratorType> activeGenerators = panel.getActiveFileBox();
                controller.getPanel(StudentListPanel.class).setGenerators(activeGenerators);
                controller.setPanel(StudentListPanel.class);
            }
        }
    }
}
