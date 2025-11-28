package ru.nsu.astakhov.autodocs.ui.view.panel;

import ru.nsu.astakhov.autodocs.document.DocumentGeneratorRegistry;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.*;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.view.GeneratorFilters;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.GeneratorPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.FileBox;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.stereotype.Component
public class GeneratorPanel extends Panel {
    private final transient DocumentGeneratorRegistry documentGeneratorRegistry;
    private final transient FilterComponent workTypeFilter;
    private final transient FilterComponent degreeFilter;
    private final transient FilterComponent academicPeriodFilter;
    private final transient FilterComponent specializationFilter;
    private final JPanel contentPanel;

    public GeneratorPanel(Controller controller, DocumentGeneratorRegistry documentGeneratorRegistry) {
        this.documentGeneratorRegistry = documentGeneratorRegistry;

        controller.addListener(this);
        setEventHandler(new GeneratorPanelEventHandler(controller, this, this::refreshDocumentsPanel));

        workTypeFilter = new FilterComponent(GeneratorFilters.WORK_TYPE);
        degreeFilter = new FilterComponent(GeneratorFilters.DEGREE);
        academicPeriodFilter = new FilterComponent(GeneratorFilters.ACADEMIC_PERIOD);
        specializationFilter = new FilterComponent(GeneratorFilters.SPECIALIZATION);

        contentPanel = initDocumentsPanel();

        configurePanel();
        refreshDocumentsPanel();
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        add(createFiltersPanel(), BorderLayout.NORTH);
        add(createDocumentsPanel(), BorderLayout.CENTER);
        add(generateButton(), BorderLayout.SOUTH);
    }

    private JPanel createFiltersPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.setBackground(focusColor);

        panel.setBorder(BorderFactory.createLineBorder(focusColor, mediumGap));

        panel.add(Box.createHorizontalGlue());
        panel.add(workTypeFilter.filterPanel);
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(degreeFilter.filterPanel);
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(academicPeriodFilter.filterPanel);
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
        verticalBar.setUnitIncrement(smallGap);

        return documentsScrollPane;
    }

    @Override
    public void onTableUpdate(String updateStatus) {
        // no operation
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        // no operation
    }

    private void refreshDocumentsPanel() {
        contentPanel.removeAll();
        updateContentPanel();
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel initDocumentsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());

        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));
        panel.setBackground(backgroundColor);

        return panel;
    }

    private void updateContentPanel() {
        final int NUM_COLUMNS = 2;

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(smallGap, smallGap, smallGap, smallGap);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;

        // TODO: объединить в один метод
        String workTypeValue = getSelectedValue(workTypeFilter.filterComboBox);
        WorkType selectedWorkType = workTypeValue == null
                ? null
                : WorkType.fromValue(workTypeValue);

        String degreeValue = getSelectedValue(degreeFilter.filterComboBox);
        Degree selectedDegree = degreeValue == null
                ? null
                : Degree.fromValue(degreeValue);

        String academicPeriodValue = getSelectedValue(academicPeriodFilter.filterComboBox);
        AcademicPeriod selectedAcademicPeriod = academicPeriodValue == null
                ? null
                : AcademicPeriod.fromValue(academicPeriodValue);

        String specializationValue = getSelectedValue(specializationFilter.filterComboBox);
        Specialization selectedSpecialization = specializationValue == null
                ? null
                : Specialization.fromValue(specializationValue);

        for (GeneratorType generatorType : documentGeneratorRegistry.getAllDocumentTypes()) {
            if (generatorType.isSuitable(selectedWorkType, selectedDegree, selectedAcademicPeriod, selectedSpecialization)) {
                contentPanel.add(new FileBox(generatorType), constraints);

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

        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap / 2));

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(ButtonCommand.SELECT_STUDENTS.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }

    public List<GeneratorType> getActiveFileBox() {
        List<GeneratorType> activeFileBox = new ArrayList<>();

        for (Component comp : contentPanel.getComponents()) {
            if (comp instanceof FileBox fileBox && fileBox.isActive()) {
                    activeFileBox.add(fileBox.getGeneratorType());
                }
        }
        return activeFileBox;
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
            label.setBorder(BorderFactory.createEmptyBorder(0, smallGap / 2,smallGap / 2, 0));

            panel.add(label, BorderLayout.NORTH);
            panel.add(comboBox, BorderLayout.CENTER);
            return panel;
        }
    }
}