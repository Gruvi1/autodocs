package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.DocumentGeneratorRegistry;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.model.WorkType;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.view.GeneratorFilters;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.GeneratorPanelEventHandler;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.FileBox;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

@Component
public class GeneratorPanel extends Panel {
    private final DocumentGeneratorRegistry documentGeneratorRegistry;
    private final FilterComponent workTypeFilter;
    private final FilterComponent degreeFilter;
    private final FilterComponent courseFilter;
    private final FilterComponent specializationFilter;
    private final JPanel contentPanel;

    public GeneratorPanel(Controller controller, DocumentGeneratorRegistry documentGeneratorRegistry) {
        this.documentGeneratorRegistry = documentGeneratorRegistry;

        controller.addListener(this);
        setEventHandler(new GeneratorPanelEventHandler(controller, this, this::refreshDocumentsPanel));

        workTypeFilter = new FilterComponent(GeneratorFilters.WORK_TYPE);
        degreeFilter = new FilterComponent(GeneratorFilters.DEGREE);
        courseFilter = new FilterComponent(GeneratorFilters.COURSE);
        specializationFilter = new FilterComponent(GeneratorFilters.SPECIALIZATION);

        contentPanel = initDocumentsPanel();

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);

        add(createFiltersPanel(), BorderLayout.NORTH);
        add(createDocumentsPanel(), BorderLayout.CENTER);
        add(generateButton(), BorderLayout.SOUTH);
    }

    private JPanel createFiltersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        panel.setBackground(focusColor);

        int mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        panel.setBorder(BorderFactory.createLineBorder(focusColor, mediumGap));

        panel.add(Box.createHorizontalGlue());
        panel.add(workTypeFilter.filterPanel);
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(degreeFilter.filterPanel);
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(courseFilter.filterPanel);
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(specializationFilter.filterPanel);
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JScrollPane createDocumentsPanel() {
        JScrollPane documentsScrollPane = new JScrollPane(contentPanel);
        documentsScrollPane.setOpaque(false);
        documentsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalBar = documentsScrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(10);

        return documentsScrollPane;
    }

    @Override
    public void onTableUpdate(String updateStatus) {}

    private void refreshDocumentsPanel() {
        contentPanel.removeAll();
        updateContentPanel();

        revalidate();
        repaint();
    }

    private JPanel initDocumentsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 5));
        panel.setBackground(backgroundColor);

        return panel;
    }

    private void updateContentPanel() {
        final int NUM_COLUMNS = 2;

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;

        String workTypeValue = getSelectedValue(workTypeFilter.filterComboBox);
        WorkType selectedWorkType = workTypeValue == null
                ? null
                : WorkType.fromValue(workTypeValue);

        String degreeValue = getSelectedValue(degreeFilter.filterComboBox);
        Degree selectedDegree = degreeValue == null
                ? null
                : Degree.fromValue(degreeValue);

        String courseValue = getSelectedValue(courseFilter.filterComboBox);
        Course selectedCourse = courseValue == null
                ? null
                : Course.fromValue(Integer.parseInt(courseValue));

        String specializationValue = getSelectedValue(specializationFilter.filterComboBox);
        Specialization selectedSpecialization = specializationValue == null
                ? null
                : Specialization.fromValue(specializationValue);

        for (GeneratorType generatorType : documentGeneratorRegistry.getAllDocumentTypes()) {
            if (generatorType.isSuitable(selectedWorkType, selectedDegree, selectedCourse, selectedSpecialization)) {
                contentPanel.add(new FileBox(generatorType.getDisplayName()), constraints);

                ++constraints.gridx;
                if (constraints.gridx % NUM_COLUMNS == 0) {
                    ++constraints.gridy;
                }
                constraints.gridx %= NUM_COLUMNS;
            }
        }
    }

    private String getSelectedValue(JComboBox<String> comboBox) {
        Object item = comboBox.getSelectedItem();
        if (item == null || "–".equals(item.toString())) {
            return null;
        }
        return item.toString();
    }

    private JPanel generateButton() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 5));

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(ButtonCommand.GENERATE.getName()));
        panel.add(Box.createHorizontalStrut(25));

        return panel;
    }

    private class FilterComponent {
        private final GeneratorFilters filterType;
        private final JComboBox<String> filterComboBox;
        private final JPanel filterPanel;

        public FilterComponent(GeneratorFilters filterType) {
            this.filterType = filterType;

            filterComboBox = createFilterComboBox();
            filterPanel = createPanelFromFilter(filterComboBox);
        }

        private JComboBox<String> createFilterComboBox() {
            Enum<? extends HasStringValue>[] constants = filterType.getEnumClass().getEnumConstants();

            String[] parameters = Arrays.stream(constants)
                    .map(constant -> ((HasStringValue)constant).getStringValue())
                    .toArray(String[]::new);

            return createComboBox(filterType.getValue(), parameters);
        }

        private JPanel createPanelFromFilter(JComboBox<String> comboBox) {
            JPanel panel = new JPanel(new BorderLayout());
            panel.setOpaque(false);

            JLabel label = new CustomLabel(filterType.getValue());
            label.setBorder(BorderFactory.createEmptyBorder(0, 5,5, 0));

            panel.add(label, BorderLayout.NORTH);
            panel.add(comboBox, BorderLayout.CENTER);
            return panel;
        }
    }
}
