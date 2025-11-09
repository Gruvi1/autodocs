package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;


public class FileBox extends JPanel {
    private final List<String> partsDescription;
    private boolean isActive;


    public FileBox(String partsDescription) {
        isActive = false;

        this.partsDescription = parseFileDescription(partsDescription);
        configureFileBox();
    }

    private List<String> parseFileDescription(String fileDescription) {
        return List.of(fileDescription.split("\n"));
    }

    private void configureFileBox() {
        setLayout(new BorderLayout());

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(focusColor);
        setBorder(BorderFactory.createLineBorder(backgroundColor, 10));

        add(createContentPanel(), BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeActive();
            }
        });
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        contentPanel.setBackground(focusColor);
        contentPanel.setBorder(BorderFactory.createLineBorder(focusColor, 25));


//        templateTypeLabel.setFont(templateTypeLabel.getFont().deriveFont(Font.BOLD));
        for (String part : partsDescription) {
            contentPanel.add(new CustomLabel(part));
        }

        return contentPanel;
    }

    private void changeActive() {
        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        isActive = !isActive;

        if (isActive) {
            setBorder(BorderFactory.createLineBorder(primaryColor, 10));
        }
        else {
            setBorder(BorderFactory.createLineBorder(backgroundColor, 10));
        }
    }
}
