package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.PlaceholderPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoLoader;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@org.springframework.stereotype.Component
public class PlaceholderPanel extends Panel implements Listener {
    private final Controller controller;

    public PlaceholderPanel(Controller controller) {
        this.controller = controller;

        controller.addListener(this);
        setEventHandler(new PlaceholderPanelEventHandler(controller, this));

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
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

        String text = "Пожалуйста, выберите нужный документ в меню слева";
        String text2 = "Чтобы посмотреть краткое руководство, нажмите сюда";

        JLabel textLabel = createTextLabel(text);
        JLabel textLabel2 = createTextLabel(text2);
        JLabel logo = LogoLoader.loadLogo(LogoType.TRANSPARENT_LOGO_IMAGE);
        logo.setAlignmentX(Component.CENTER_ALIGNMENT);

        int gap = 4 * Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));
        List.of(Box.createVerticalStrut(gap), logo, textLabel, textLabel2, Box.createVerticalGlue()).
                    forEach(contentPanel::add);
        List.of(Box.createHorizontalGlue(), contentPanel, Box.createHorizontalGlue()).
                    forEach(wrapperPanel::add);

        return wrapperPanel;
    }

    private JLabel createTextLabel(String text) {
        JLabel textLabel = new JLabel(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TITLE_SIZE));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        textLabel.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_ITALIC, textSize));
        textLabel.setForeground(textColor);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return textLabel;
    }

    @Override
    public void onTableUpdate(String updateStatus) {
    }
}
