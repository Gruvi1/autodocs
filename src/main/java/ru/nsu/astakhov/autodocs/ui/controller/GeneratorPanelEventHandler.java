package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

@RequiredArgsConstructor
@Component
public class GeneratorPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final GeneratorPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source instanceof JComboBox<?> comboBox) {
            System.out.println(comboBox.getSelectedItem());
        }
    }
}
