package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.model.TemplateType;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileBox extends JPanel {
    private final TemplateType templateType;
    private final Degree degree;
    private final Course course;
    private final Specialization specialization;
    private boolean isActive;

    public FileBox(TemplateType templateType, Degree degree, Course course, Specialization specialization) {
        this.templateType = templateType;
        this.degree = degree;
        this.course = course;
        this.specialization = specialization;

        isActive = false;

        configureFileBox();
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

        String temp = degree.getValue() + ", " + course.getValue() + " курс";
        JLabel templateTypeLabel = new CustomLabel(templateType.getValue());

        templateTypeLabel.setFont(templateTypeLabel.getFont().deriveFont(Font.BOLD));

        contentPanel.add(templateTypeLabel);
        contentPanel.add(new CustomLabel(temp));
        contentPanel.add(new CustomLabel(specialization.getValue()));

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
