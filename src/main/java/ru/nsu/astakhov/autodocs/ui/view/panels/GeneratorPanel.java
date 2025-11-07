package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.GeneratorFilters;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.TypeFilter;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.GeneratorPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.CourseFilter;
import ru.nsu.astakhov.autodocs.ui.view.DegreeFilter;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Component
public class GeneratorPanel extends Panel implements Listener {
    private final Controller controller;

    public GeneratorPanel(Controller controller) {
        this.controller = controller;

        controller.addListener(this);
        setEventHandler(new GeneratorPanelEventHandler(controller, this));

        configurePanel();
    }


    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
//        setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));

        add(createFilterPanel(), BorderLayout.NORTH);
//        add(createDocumentsPanel(), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setOpaque(false);
        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        panel.setBackground(focusColor);

        panel.setBorder(BorderFactory.createLineBorder(focusColor, 10));

        panel.add(Box.createHorizontalGlue());
        panel.add(createTypeFilter());
        panel.add(Box.createHorizontalStrut(25));
        panel.add(createDegreeFilter());
        panel.add(Box.createHorizontalStrut(25));
        panel.add(createCourseFilter());
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    private JComboBox<String> createTypeFilter() {
        String[] parameters = Arrays.stream(TypeFilter.values())
                .map(TypeFilter::getValue)
                .toArray(String[]::new);

        return createComboBox(GeneratorFilters.TYPE.getValue(), parameters);
    }

    private JComboBox<String> createDegreeFilter() {
        String[] parameters = Arrays.stream(DegreeFilter.values())
                .map(DegreeFilter::getValue)
                .toArray(String[]::new);

        return createComboBox(GeneratorFilters.DEGREE.getValue(), parameters);
    }

    private JComboBox<String> createCourseFilter() {
        String[] parameters = Arrays.stream(CourseFilter.values())
                .map(CourseFilter::getValue)
                .toArray(String[]::new);

        return createComboBox(GeneratorFilters.COURSE.getValue(), parameters);
    }

    private JPanel createDocumentsPanel() {
        JPanel panel = new JPanel(new GridLayout(11, 3));
        panel.setOpaque(false);

        return panel;
    }

    @Override
    public void onTableUpdate(String updateStatus) {

    }
}
