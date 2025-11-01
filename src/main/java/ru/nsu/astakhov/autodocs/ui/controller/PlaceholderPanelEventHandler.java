package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.PlaceholderPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class PlaceholderPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final PlaceholderPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
