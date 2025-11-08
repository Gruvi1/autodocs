package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.NavigationPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoLoader;
import ru.nsu.astakhov.autodocs.ui.view.logo.LogoType;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;

@org.springframework.stereotype.Component
public class NavigationPanel extends Panel implements Listener {
    public NavigationPanel(Controller controller) {
        controller.addListener(this);
        setEventHandler(new NavigationPanelEventHandler(controller,this));

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.TEXT_SIZE));

        setBackground(primaryColor);
        setForeground(textColor);
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        setBorder(BorderFactory.createLineBorder(primaryColor, smallGap));

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
                createButton(ButtonCommand.WARNING_TABLE.getName()),
                createSeparator(),
                createButton(ButtonCommand.APPLICATION_TEMPLATES.getName()),
                createButton(ButtonCommand.CREATE_APPLICATION_TEMPLATE.getName()),
                createSeparator(),
                createButton(ButtonCommand.GENERATE_DOCUMENT.getName()),
                Box.createVerticalGlue(),
                createLogoLabel()
        ));

        for (Component component : components) {
            buttonPanel.add(component);
            buttonPanel.add(Box.createVerticalStrut(smallGap));
        }

        int menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        JButton guideButton = createButton(ButtonCommand.SHORT_GUIDE.getName());
        guideButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));

        buttonPanel.add(guideButton);

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

    private JLabel createLogoLabel() {
        int widthLogo = 350;
        int heightLogo = 100;

        return LogoLoader.loadLogo(LogoType.HORIZONTAL_LOGO, widthLogo, heightLogo);
    }

    @Override
    public void onTableUpdate(String updateStatus) {
    }
}
