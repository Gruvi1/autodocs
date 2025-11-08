package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.documents.GeneratorType;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.model.TableType;
import ru.nsu.astakhov.autodocs.ui.view.GeneratorFilters;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.GeneratorPanelEventHandler;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Component
public class GeneratorPanel extends Panel implements Listener {
    public GeneratorPanel(Controller controller) {
        controller.addListener(this);
        setEventHandler(new GeneratorPanelEventHandler(controller, this));

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);

        add(createFilterPanel(), BorderLayout.NORTH);
        add(createDocumentsPanel(), BorderLayout.CENTER);
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        panel.setBackground(focusColor);

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        int mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        panel.setBorder(BorderFactory.createLineBorder(focusColor, 2 * smallGap));

        panel.add(Box.createHorizontalGlue());
        panel.add(createTypeFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createDegreeFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createCourseFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createSpecializationFilter());
        panel.add(Box.createHorizontalGlue());
        return panel;
    }
    private JPanel createFilter(String[] parameters, GeneratorFilters type) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        String filterType = type.getValue();

        JComboBox<String> comboBox = createComboBox(filterType, parameters);

        JLabel label = new CustomLabel(filterType);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5,5, 0));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createDegreeFilter() {
        String[] parameters = Arrays.stream(Degree.values())
                .map(Degree::getValue)
                .toArray(String[]::new);

        return createFilter(parameters, GeneratorFilters.DEGREE);
    }

    private JPanel createCourseFilter() {
        String[] parameters = Arrays.stream(Course.values())
                .map(Course::getValue)
                .map(Object::toString)
                .toArray(String[]::new);

        return createFilter(parameters, GeneratorFilters.COURSE);
    }

    private JPanel createSpecializationFilter() {
        String[] parameters = Arrays.stream(Specialization.values())
                .map(Specialization::getValue)
                .toArray(String[]::new);

        return createFilter(parameters, GeneratorFilters.SPECIALIZATION);
    }

    private JPanel createTypeFilter() {
        String[] parameters = Arrays.stream(TableType.values())
                .map(TableType::getValue)
                .toArray(String[]::new);

        return createFilter(parameters, GeneratorFilters.TYPE);
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
