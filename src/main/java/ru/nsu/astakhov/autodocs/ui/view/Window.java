package ru.nsu.astakhov.autodocs.ui.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.utils.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.utils.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;

@Component
public class Window extends JFrame {
    private static final Logger logger = LoggerFactory.getLogger(Window.class);

    public Window() {
        configureWindow();
        createScreen();
        logger.info("Window initialized");
    }

    private void configureWindow() {
        setTitle(ConfigManager.getSetting(ConfigConstants.APP_NAME));

        int window_width = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_WIDTH));
        int window_height = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_HEIGHT));
        setSize(window_width, window_height);

        // TODO: более умно завершать работу
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        boolean maximized = Boolean.parseBoolean(ConfigManager.getSetting(ConfigConstants.MAXIMIZED));
        if (maximized) {
            setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
    }

    private void createScreen() {
        super.getContentPane().removeAll();

        super.add(new MainScreen().create(), BorderLayout.CENTER);
//        controller.setCurrentScreen(currentScreen);

        super.revalidate();
        super.repaint();
    }
}
