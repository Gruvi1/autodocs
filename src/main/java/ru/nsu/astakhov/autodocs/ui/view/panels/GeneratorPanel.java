package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.DocumentGeneratorRegistry;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.model.WorkType;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.view.GeneratorFilters;
import ru.nsu.astakhov.autodocs.ui.Listener;
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
public class GeneratorPanel extends Panel implements Listener {
    private final DocumentGeneratorRegistry documentGeneratorRegistry;
    private JScrollPane documentsScrollPane;
    private JComboBox<String> workTypeFilter;
    private JComboBox<String> degreeFilter;
    private JComboBox<String> courseFilter;
    private JComboBox<String> specializationFilter;

    public GeneratorPanel(Controller controller, DocumentGeneratorRegistry documentGeneratorRegistry) {
        this.documentGeneratorRegistry = documentGeneratorRegistry;
        controller.addListener(this);
        setEventHandler(new GeneratorPanelEventHandler(controller, this, this::refreshDocumentsPanel));

        configurePanel();
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);


        // TODO: подумать, как исправить
        // TODO: сейчас порядок инициализации влияет на работу программы
        // TODO: если поменять NORTH и CENTER местами, то посыпятся NullPointerException
        add(createFilterPanel(), BorderLayout.NORTH);

        documentsScrollPane = createDocumentsPanel();
        add(documentsScrollPane, BorderLayout.CENTER);

        add(generateButton(), BorderLayout.SOUTH);
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
        panel.add(createWorkTypeFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createDegreeFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createCourseFilter());
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createSpecializationFilter());
        panel.add(Box.createHorizontalGlue());
        return panel;
    }
    private JPanel configureFilter(JComboBox<String> comboBox, String filterName) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        JLabel label = new CustomLabel(filterName);
        label.setBorder(BorderFactory.createEmptyBorder(0, 5,5, 0));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createWorkTypeFilter() {
        String[] parameters = Arrays.stream(WorkType.values())
                .map(WorkType::getValue)
                .toArray(String[]::new);

        String filterName = GeneratorFilters.WORK_TYPE.getValue();
        workTypeFilter = createComboBox(filterName, parameters);

        return configureFilter(workTypeFilter, filterName);
    }

    private JPanel createDegreeFilter() {
        String[] parameters = Arrays.stream(Degree.values())
                .map(Degree::getValue)
                .toArray(String[]::new);

        String filterName = GeneratorFilters.DEGREE.getValue();
        degreeFilter = createComboBox(filterName, parameters);

        return configureFilter(degreeFilter, filterName);
    }

    private JPanel createCourseFilter() {
        String[] parameters = Arrays.stream(Course.values())
                .map(Course::getValue)
                .map(Object::toString)
                .toArray(String[]::new);

        String filterName = GeneratorFilters.COURSE.getValue();
        courseFilter = createComboBox(filterName, parameters);

        return configureFilter(courseFilter, filterName);
    }

    private JPanel createSpecializationFilter() {
        String[] parameters = Arrays.stream(Specialization.values())
                .map(Specialization::getValue)
                .toArray(String[]::new);

        String filterName = GeneratorFilters.SPECIALIZATION.getValue();
        specializationFilter = createComboBox(filterName, parameters);

        return configureFilter(specializationFilter, filterName);
    }


    private JScrollPane createDocumentsPanel() {
        documentsScrollPane = new JScrollPane(buildDocumentsPanel());
        documentsScrollPane.setOpaque(false);
        documentsScrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalBar = documentsScrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(10);

        return documentsScrollPane;
    }

    @Override
    public void onTableUpdate(String updateStatus) {}

    private void refreshDocumentsPanel() {
        documentsScrollPane.setViewportView(new JPanel());

        JPanel newPanel = buildDocumentsPanel();
        documentsScrollPane.setViewportView(newPanel);
    }

    private JPanel buildDocumentsPanel() {
        int numColumns = 2;

        JPanel panel = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, 5));
        panel.setBackground(backgroundColor);

        // Получаем текущие значения фильтров (null = "любой")
        String workTypeValue = getSelectedValue(workTypeFilter);
        WorkType selectedWorkType = workTypeValue == null
                ? null
                : WorkType.fromValue(workTypeValue);

        String degreeValue = getSelectedValue(degreeFilter);
        Degree selectedDegree = degreeValue == null
                ? null
                : Degree.fromValue(degreeValue);

        String courseValue = getSelectedValue(courseFilter);
        Course selectedCourse = courseValue == null
                ? null
                : Course.fromValue(Integer.parseInt(courseValue));

        String specializationValue = getSelectedValue(specializationFilter);
        Specialization selectedSpecialization = specializationValue == null
                ? null
                : Specialization.fromValue(specializationValue);

        for (GeneratorType generatorType : documentGeneratorRegistry.getAllDocumentTypes()) {
            if (generatorType.isSuitable(selectedWorkType, selectedDegree, selectedCourse, selectedSpecialization)) {
                panel.add(new FileBox(generatorType.getDisplayName()), constraints);

                ++constraints.gridx;
                if (constraints.gridx % numColumns == 0) {
                    ++constraints.gridy;
                }
                constraints.gridx %= numColumns;
            }
        }

        return panel;
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
}
