package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class NavigationPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final NavigationPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        ButtonCommand buttonCommand = ButtonCommand.fromString(command);

        switch (buttonCommand) {
            case UPDATE_TABLE   -> controller.updateTable();
            case ALL_DOC -> {
                Frame owner = JOptionPane.getFrameForComponent((java.awt.Component) e.getSource());
                controller.testShowDialog(owner);
            }
            default             -> {}
        }
    }
}
