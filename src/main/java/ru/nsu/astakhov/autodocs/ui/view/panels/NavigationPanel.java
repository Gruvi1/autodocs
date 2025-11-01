package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.context.annotation.Scope;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoLoader;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoType;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

@Scope("prototype")
@org.springframework.stereotype.Component
public class NavigationPanel extends Panel {

    public NavigationPanel(ButtonEventHandler buttonEventHandler) {
        super(buttonEventHandler);
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));

        setBackground(primaryColor);
        setForeground(textColor);
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));

        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, focusColor));


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
                createButton(ButtonCommand.UPDATE_TABLE.getName()),
                createSeparator(),
                createButton(ButtonCommand.APPLICATION_TEMPLATES.getName()),
                createButton(ButtonCommand.CREATE_APPLICATION_TEMPLATE.getName()),
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
        int separatorHeight = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

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
        label.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        label.setForeground(textColor);

        return label;
    }

    private JLabel createLogoLabel() {
        int widthLogo = 350;
        int heightLogo = 100;

        return LogoLoader.loadLogo(LogoType.HORIZONTAL_LOGO, widthLogo, heightLogo);
    }
}
