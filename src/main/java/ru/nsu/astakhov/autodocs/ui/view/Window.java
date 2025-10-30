package ru.nsu.astakhov.autodocs.ui.view;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.panels.NavigationPanel;
import ru.nsu.astakhov.autodocs.ui.view.panels.PlaceholderPanel;

import javax.swing.*;
import java.awt.*;

@Slf4j
@Component
public class Window extends JFrame {
    public Window() {
        configureWindow();
        createWindow();
        logger.info("Window initialized");
    }

    private void configureWindow() {
        setTitle(ConfigManager.getSetting(ConfigConstants.APP_NAME));

        int window_width = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_WIDTH));
        int window_height = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_HEIGHT));
        setSize(window_width, window_height);

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
        super.getContentPane().removeAll();

        super.add(new NavigationPanel(), BorderLayout.WEST);
        super.add(new PlaceholderPanel(), BorderLayout.CENTER);

        //        super.add(new MainScreen().create(), BorderLayout.CENTER);
//        controller.setCurrentScreen(currentScreen);

        super.revalidate();
        super.repaint();
    }

    private void updateWindow() {}
}
