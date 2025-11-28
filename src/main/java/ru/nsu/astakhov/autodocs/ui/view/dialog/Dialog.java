package ru.nsu.astakhov.autodocs.ui.view.dialog;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;

public abstract class Dialog extends JDialog {
    private Component originalGlassPane;
    private JPanel overlay;
    protected final int smallGap;
    protected final int mediumGap;

    protected final int menuTextSize;
    protected final int textSize;
    protected final int titleTextSize;

    protected final Color primaryColor;
    protected final Color backgroundColor;
    protected final Color focusColor;
    protected final Color textColor;

    protected Dialog(Frame owner, String dialogName) {
        super(owner, dialogName, true);
        this.smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        this.mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        this.menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        this.textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));
        this.titleTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TITLE_SIZE));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        this.textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
    }

    protected abstract void configureDialog();

    protected void createOverlay(Frame owner) {
        if (owner == null) {
            return;
        }

        JRootPane rootPane = SwingUtilities.getRootPane(owner);
        if (rootPane == null) {
            return;
        }

        originalGlassPane = rootPane.getGlassPane();

        overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 64));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        overlay.setOpaque(false);
        rootPane.setGlassPane(overlay);
        overlay.setVisible(true);
    }

    protected void removeOverlay(Frame owner) {
        if (owner == null || overlay == null) {
            return;
        }
        overlay.setVisible(false);

        JRootPane rootPane = SwingUtilities.getRootPane(owner);

        if (rootPane != null) {
            rootPane.setGlassPane(originalGlassPane);
            rootPane.getGlassPane().setVisible(false);
        }

        overlay = null;
        originalGlassPane = null;
    }
}