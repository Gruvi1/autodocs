package ru.nsu.astakhov.autodocs.ui.view.component;

import lombok.Getter;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

@Getter
public class FileBox extends JPanel {
    private final GeneratorType generatorType;
    private boolean isActive;

    private final int smallGap;
    private final int mediumGap;

    private final Color primaryColor;
    private final Color backgroundColor;
    private final Color focusColor;

    public FileBox(GeneratorType generatorType) {
        this.generatorType = generatorType;
        isActive = false;

        this.smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        this.mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));

        configureFileBox();
    }

    private List<String> parseFileDescription(String fileDescription) {
        return List.of(fileDescription.split("\n"));
    }

    private void configureFileBox() {
        setLayout(new BorderLayout());

        setBackground(focusColor);
        setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));

        add(createContentPanel(), BorderLayout.CENTER);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isEnabled()) {
                    changeActive();
                }
            }
        });
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        contentPanel.setBackground(focusColor);
        contentPanel.setBorder(BorderFactory.createLineBorder(focusColor, mediumGap));

        List<String> partsDescription = parseFileDescription(generatorType.getDisplayName());

        for (int i = 0; i != partsDescription.size(); ++i) {
            if (i == 0) {
                JLabel firstPanel = new CustomLabel(partsDescription.get(i));
                firstPanel.setFont(firstPanel.getFont().deriveFont(Font.BOLD));
                contentPanel.add(firstPanel);
            }
            else {
                contentPanel.add(new CustomLabel(partsDescription.get(i)));
            }
        }

        return contentPanel;
    }

    private void changeActive() {
        isActive = !isActive;

        if (isActive) {
            setBorder(BorderFactory.createLineBorder(primaryColor, 10));
        }
        else {
            setBorder(BorderFactory.createLineBorder(backgroundColor, 10));
        }
    }
}
