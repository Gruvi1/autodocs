package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneratorCard extends JPanel {
    private final GeneratorType generator;
    private final Controller controller;
    private final Map<StudentDto, SelectableLabel> students;

    private final int smallGap;
    private final int mediumGap;

    private final int menuTextSize;

    private final Color primaryColor;
    private final Color focusColor;

    public GeneratorCard(GeneratorType generator, Controller controller) {
        this.generator = generator;
        this.controller = controller;
        this.students = new HashMap<>();

        this.smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        this.mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));

        this.menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));

        configureCard();
    }

    public List<StudentDto> getAllStudents() {
        return students.keySet().stream().toList();
    }

    public List<StudentDto> getSelectedStudents() {
        List<StudentDto> selectedStudents = new ArrayList<>();
        for (StudentDto student : students.keySet()) {
            SelectableLabel label = students.get(student);
            if (label != null && label.isActive()) {
                selectedStudents.add(student);
            }
        }
        return selectedStudents;
    }
    private void configureCard() {
        setLayout(new GridBagLayout());
        setBackground(focusColor);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap)
        ));
        setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridy = 0;
        constraints.gridx = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.0;


        add(createTitlePanel(), constraints);
        ++constraints.gridy;

        add(createSelectPanel(), constraints);
        ++constraints.gridy;

        constraints.weighty = 1.0;
        add(createStudentLinesPanel(generator), constraints);
    }

    private JPanel createTitlePanel() {
        FileBox fileBox = new FileBox(generator);
        fileBox.setBorder(BorderFactory.createEmptyBorder());
        fileBox.setEnabled(false);

        return fileBox;
    }

    private JPanel createSelectPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        String selectAllButtonName = ButtonCommand.SELECT_ALL.getName();
        RoundedButton selectAllButton = new RoundedButton(selectAllButtonName, 0);
        selectAllButton.setActionCommand(selectAllButtonName);
        selectAllButton.addActionListener((e) -> {
            for (SelectableLabel student : students.values()) {
                student.setActive(true);
            }
        });
        selectAllButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        selectAllButton.setBorder(BorderFactory.createEmptyBorder(smallGap, mediumGap * 2, smallGap, mediumGap * 2));

        String removeAllButtonName = ButtonCommand.REMOVE_ALL.getName();
        RoundedButton removeAllButton = new RoundedButton(removeAllButtonName, 0);
        removeAllButton.setActionCommand(removeAllButtonName);
        removeAllButton.addActionListener((e) -> {
            for (SelectableLabel student : students.values()) {
                student.setActive(false);
            }
        });
        removeAllButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        removeAllButton.setBorder(BorderFactory.createEmptyBorder(smallGap, mediumGap * 2, smallGap, mediumGap * 2));

        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(selectAllButton);
        panel.add(Box.createHorizontalGlue());
        panel.add(removeAllButton);
        panel.add(Box.createHorizontalStrut(mediumGap));
        return panel;
    }

    private JScrollPane createStudentLinesPanel(GeneratorType generator) {
        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        lines.setBackground(focusColor);
        lines.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        List<StudentDto> students = controller.getStudentsByGenerator(generator);
        for (StudentDto student : students) {
            JPanel studentLine = createStudentLine(student);
            lines.add(studentLine);
            lines.add(Box.createVerticalStrut(smallGap));
        }

        JScrollPane scrollPane = new JScrollPane(lines);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(smallGap);

        return scrollPane;
    }

    private JPanel createStudentLine(StudentDto student) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(focusColor);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(smallGap / 2, smallGap, smallGap / 2, smallGap)
        ));

        SelectableLabel studentLabel = new SelectableLabel(student.fullName());
        students.put(student, studentLabel);

        panel.add(studentLabel);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));

        return panel;
    }
}
