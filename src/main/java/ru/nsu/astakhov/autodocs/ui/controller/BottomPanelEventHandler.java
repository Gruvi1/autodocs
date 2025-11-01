package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.BottomPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class BottomPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final BottomPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
