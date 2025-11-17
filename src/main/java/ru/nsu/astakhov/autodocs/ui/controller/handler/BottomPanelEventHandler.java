package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panels.BottomPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class BottomPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final BottomPanel bottomPanel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // no operation
    }
}
