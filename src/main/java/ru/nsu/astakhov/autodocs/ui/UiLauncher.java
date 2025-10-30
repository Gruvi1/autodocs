package ru.nsu.astakhov.autodocs.ui;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.Window;

import javax.swing.*;

@RequiredArgsConstructor
@Component
public class UiLauncher {
    private final Window window;

    public void launch() {
        SwingUtilities.invokeLater(() -> window.setVisible(true));
    }
}
