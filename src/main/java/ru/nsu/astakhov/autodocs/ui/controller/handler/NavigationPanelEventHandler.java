package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.WarningsPanel;

import javax.swing.*;
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
            case GENERATE_DOCUMENT              -> controller.setPanel(GeneratorPanel.class);
        }
    }
}
