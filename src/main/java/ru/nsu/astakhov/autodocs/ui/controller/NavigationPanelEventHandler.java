package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.WarningsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class NavigationPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final NavigationPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        ButtonCommand buttonCommand = ButtonCommand.fromString(command);

        switch (buttonCommand) {
            case UPDATE_TABLE                   -> controller.updateTable(JOptionPane.getFrameForComponent(panel));
            case WARNING_TABLE                  -> controller.setPanel(WarningsPanel.class);
            case APPLICATION_TEMPLATES          -> {}
            case CREATE_APPLICATION_TEMPLATE    -> {}
            case GENERATE_DOCUMENT              -> controller.setPanel(GeneratorPanel.class);
//            case INDIVIDUAL_ASSIGNMENT -> controller.createIndWorkDoc(); // TODO: как тут было раньше
            default             -> {}
        }
    }
}
