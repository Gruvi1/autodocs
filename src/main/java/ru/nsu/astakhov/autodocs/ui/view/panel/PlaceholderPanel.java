package ru.nsu.astakhov.autodocs.ui.view.panel;

import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.PlaceholderPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoLoader;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@org.springframework.stereotype.Component
public class PlaceholderPanel extends Panel {
    public PlaceholderPanel(Controller controller) {
        controller.addListener(this);
        setEventHandler(new PlaceholderPanelEventHandler(controller, this));

        configurePanel();
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        add(createPlaceholder(), BorderLayout.CENTER);
    }

    private JPanel createPlaceholder() {
        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.X_AXIS));
        wrapperPanel.setOpaque(false);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        String text  = "Выберите действие в левом меню";
        String text2 = "Посмотрите краткое руководство, чтобы начать";

        JLabel textLabel = createTextLabel(text);
        JLabel textLabel2 = createTextLabel(text2);
        JLabel logo = LogoLoader.loadLogo(LogoType.TRANSPARENT_LOGO_IMAGE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        int gap = 4 * mediumGap;
        List.of(Box.createVerticalStrut(gap), logo, textLabel, textLabel2, Box.createVerticalGlue()).
                    forEach(contentPanel::add);
        List.of(Box.createHorizontalGlue(), contentPanel, Box.createHorizontalGlue()).
                    forEach(wrapperPanel::add);

        return wrapperPanel;
    }

    private JLabel createTextLabel(String text) {
        JLabel textLabel = new CustomLabel(text);

        textLabel.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_ITALIC, titleTextSize));
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return textLabel;
    }

    @Override
    public void onTableUpdate(String updateStatus) {
        // no operation
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        // no operation
    }
}
