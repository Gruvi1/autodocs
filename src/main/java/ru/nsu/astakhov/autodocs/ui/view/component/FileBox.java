package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.TemplateType;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;

import javax.swing.*;
import java.awt.*;

public class FileBox extends JPanel {
    private final TemplateType templateType;
    private final Degree degree;
    private final Course course;
    private final Specialization specialization;

    public FileBox(TemplateType templateType, Degree degree, Course course, Specialization specialization) {
        this.templateType = templateType;
        this.degree = degree;
        this.course = course;
        this.specialization = specialization;

        configureFileBox();
    }

    private void configureFileBox() {
        setLayout(new BorderLayout());

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(focusColor);
        setBorder(BorderFactory.createLineBorder(backgroundColor, 5));

        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
//        contentPanel.setOpaque(false);

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        contentPanel.setBackground(focusColor);
        contentPanel.setBorder(BorderFactory.createLineBorder(focusColor, 5));

        String temp = degree.getValue() + ", " + course.getValue() + " курс";

        contentPanel.add(new CustomLabel(templateType.getValue()));
        contentPanel.add(new CustomLabel(temp));
//        contentPanel.add(new CustomLabel(degree.getValue()));
//        contentPanel.add(new CustomLabel(String.valueOf(course.getValue())));
        contentPanel.add(new CustomLabel(specialization.getValue()));

        return contentPanel;
    }
}
