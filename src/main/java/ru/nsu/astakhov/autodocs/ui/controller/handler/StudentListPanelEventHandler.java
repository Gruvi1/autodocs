package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panel.StudentListPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class StudentListPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final StudentListPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // no operation
    }
}
