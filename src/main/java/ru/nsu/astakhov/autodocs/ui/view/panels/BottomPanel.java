package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.BottomPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;

import javax.swing.*;
import java.awt.*;

@Component
public class BottomPanel extends Panel {
    private final JLabel statusLabel;

    public BottomPanel(Controller controller) {
        statusLabel = new CustomLabel("");

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

    @Override
    public void onTableUpdate(String updateStatus) {
        statusLabel.setText(updateStatus);
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        statusLabel.setText(generateStatus);
    }
}
