package ru.nsu.astakhov.autodocs.core.view;

import ru.nsu.astakhov.autodocs.core.utils.ConfigConstants;
import ru.nsu.astakhov.autodocs.core.utils.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    public Window() {
        configureWindow();
        createScreen();
    }

    private void configureWindow() {
        setTitle(ConfigManager.getSetting(ConfigConstants.APP_NAME));

        int window_width = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_WIDTH));
        int window_height = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.WINDOW_HEIGHT));
        setSize(window_width, window_height);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
