package ru.nsu.astakhov.autodocs.ui.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.panels.BottomPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.GeneratorPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.PlaceholderPanel;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class Window extends JFrame {
    private final transient PanelManager panelManager;

    public Window(PanelManager panelManager) {
        this.panelManager = panelManager;
        panelManager.setWindow(this);
        configureWindow();
        createWindow();
        logger.info("Window initialized");
    }

    private void configureWindow() {
        setTitle(ConfigManager.getSetting(ConfigConstants.APP_NAME));

        int width = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_WIDTH));
        int height = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_HEIGHT));
        setSize(width, height);

        // TODO: более умно завершать работу
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        boolean maximized = Boolean.parseBoolean(ConfigManager.getSetting(ConfigConstants.MAXIMIZED));
        if (maximized) {
            setExtendedState(Frame.MAXIMIZED_BOTH);
        }
    }

    private void createWindow() {
        panelManager.setPanel(NavigationPanel.class);
//        panelManager.setPanel(PlaceholderPanel.class);
        panelManager.setPanel(GeneratorPanel.class);
        panelManager.setPanel(BottomPanel.class);
    }
}
