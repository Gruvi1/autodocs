package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontTypes;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class NavigationPanel extends Panel {
    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));

        setBackground(primaryColor);
        setForeground(textColor);
        setFont(FontLoader.loadFont(FontTypes.ADWAITA_SANS_REGULAR, textSize));

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        add(Box.createVerticalStrut(smallGap), BorderLayout.NORTH);
        add(Box.createVerticalStrut(smallGap), BorderLayout.SOUTH);
        add(Box.createHorizontalStrut(smallGap), BorderLayout.WEST);
        add(Box.createHorizontalStrut(smallGap), BorderLayout.EAST);
        add(createButtonPanel(), BorderLayout.CENTER);
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        buttonPanel.setBackground(primaryColor);

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        List<Component> components = new ArrayList<>(List.of(
                createButton(ButtonCommand.APPLICATION_TEMPLATES.getName()),
                createButton(ButtonCommand.CREATE_APPLICATION_TEMPLATE.getName()),
                createButton(ButtonCommand.THREE.getName()),
                createSeparator(),
                createLabel(),
                createButton(ButtonCommand.ALL_DOC.getName()),
                createButton(ButtonCommand.INTERNSHIP_APPLICATION.getName()),
                createButton(ButtonCommand.INDIVIDUAL_ASSIGNMENT.getName()),
                createButton(ButtonCommand.INTERNSHIP_REPORT.getName()),
                createButton(ButtonCommand.REVIEW.getName()),
                createButton(ButtonCommand.REVIEWER_COMMENT.getName()),
                createButton(ButtonCommand.THESIS_SUPERVISOR_REVIEW.getName()),
                Box.createVerticalGlue(),
                createLogoLabel()
        ));

        for (Component component : components) {
            buttonPanel.add(component);
            buttonPanel.add(Box.createVerticalStrut(smallGap));
        }
        return buttonPanel;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        int separatorHeight = 5;

        separator.setOpaque(true);
        separator.setForeground(focusColor);
        separator.setBackground(focusColor);
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, separatorHeight));

        return separator;
    }

    private JLabel createLabel() {
        final String textLabel = "Сгенерировать:";
        JLabel label = new JLabel(textLabel);

        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));
        label.setFont(FontLoader.loadFont(FontTypes.ADWAITA_SANS_REGULAR, textSize));
        label.setForeground(textColor);

        return label;
    }

    public JLabel createLogoLabel() {
        final int widthLogo = 350;
        final int heightLogo = 100;

        JLabel label;

        // TODO: подумать над формированием пути
        String logo_path = "/logo/" + ConfigManager.getSetting(ConfigConstants.THEME) + "_logo_h.png";
        try {
            BufferedImage rawImage =
                    ImageIO.read(Objects.requireNonNull(NavigationPanel.class.getResourceAsStream(logo_path)));
            Image scaledImage = rawImage.getScaledInstance(widthLogo, heightLogo, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(scaledImage);

            label = new JLabel(icon);
        }
        catch (RuntimeException | IOException e) {
            label = new JLabel();
        }

        return label;
    }
}
