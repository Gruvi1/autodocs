package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panel.PlaceholderPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class PlaceholderPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final PlaceholderPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // no operation
    }
}
