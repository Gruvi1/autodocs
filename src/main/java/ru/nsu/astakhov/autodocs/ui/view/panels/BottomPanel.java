package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.controller.handler.BottomPanelEventHandler;
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
    protected void configurePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        int height = smallGap + mediumGap;
        setPreferredSize(new Dimension(0, height));

        setBackground(primaryColor);
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, focusColor));

        add(Box.createHorizontalGlue());
        add(statusLabel);
        add(Box.createHorizontalStrut(mediumGap));
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
