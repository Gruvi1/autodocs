package ru.nsu.astakhov.autodocs.ui.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.PlaceholderPanel;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class MainScreen extends Screen {
    @Override
    void setupKeyBindings(JPanel panel) {}

    @Override
    JPanel create() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        mainPanel.add(new NavigationPanel(), BorderLayout.WEST);
//        mainPanel.add(new ContentPanel(), BorderLayout.CENTER);
        mainPanel.add(new PlaceholderPanel(), BorderLayout.CENTER);
        return mainPanel;
    }
}
