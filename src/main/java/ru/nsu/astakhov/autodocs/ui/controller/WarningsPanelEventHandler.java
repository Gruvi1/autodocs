package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.WarningsPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class WarningsPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final WarningsPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
