package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.BottomPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

@Component
public class BottomPanel extends Panel implements Listener {
    private final JLabel statusLabel;
    private final Controller controller;

    public BottomPanel(Controller controller) {
        this.controller = controller;
        statusLabel = createStatusLabel();

        controller.addListener(this);
        setEventHandler(new BottomPanelEventHandler(controller, this));

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        int mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        int height = smallGap + mediumGap;
        setPreferredSize(new Dimension(0, height));

        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        setBackground(primaryColor);
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, focusColor));

        int gap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));
        add(Box.createHorizontalGlue());
        add(statusLabel);
        add(Box.createHorizontalStrut(gap));
    }

    private JLabel createStatusLabel() {
        JLabel label = new JLabel();

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        label.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));

        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        label.setForeground(textColor);

        return label;
    }

    @Override
    public void onTableUpdate(String updateStatus) {
        statusLabel.setText(updateStatus);
    }
}
