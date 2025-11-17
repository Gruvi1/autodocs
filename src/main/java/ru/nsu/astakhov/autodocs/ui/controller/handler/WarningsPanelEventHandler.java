package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panel.WarningsPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class WarningsPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final WarningsPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // no operation
    }
}
