package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.CustomComboBox;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;

import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class GeneratorPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final GeneratorPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        System.out.println(e.getSource());

        CustomComboBox source = (CustomComboBox) e.getSource();

        System.out.println(source.getSelectedItem());
    }
}
