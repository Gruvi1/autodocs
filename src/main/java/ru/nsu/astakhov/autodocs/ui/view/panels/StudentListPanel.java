package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.StudentListPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.FileBox;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.List;


@Component
public class StudentListPanel extends Panel {
    private final transient Controller controller;
    private List<GeneratorType> activeGenerators;
    private final JPanel contentPanel;

    public StudentListPanel(Controller controller) {
        this.controller = controller;

        contentPanel = initContentPanel();

        controller.addListener(this);
        setEventHandler(new StudentListPanelEventHandler(controller,this));

        configurePanel();
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        add(createStudentListsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }


    private JScrollPane createStudentListsPanel() {

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(smallGap);

        return scrollPane;
    }

    private JPanel initContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));
        panel.setBackground(backgroundColor);

        return panel;
    }

    private JPanel createStudentListPanel(GeneratorType generator) {
        JPanel panel = new JPanel(new BorderLayout(smallGap, smallGap));
        panel.setOpaque(false);

        panel.setBackground(focusColor);

        FileBox fileBox = new FileBox(generator);
        fileBox.setBorder(BorderFactory.createEmptyBorder());
        fileBox.setEnabled(false);
        fileBox.setOpaque(false);

        JButton generateAllButton = createButton(ButtonCommand.SELECT_ALL.getName());
        generateAllButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));

        panel.add(fileBox, BorderLayout.NORTH);
        panel.add(generateAllButton, BorderLayout.CENTER);
        panel.add(createStudentLinesPanel(generator), BorderLayout.SOUTH);

        return panel;
    }

    private JScrollPane createStudentLinesPanel(GeneratorType generator) {
        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));

        lines.setBackground(focusColor);

        List<StudentDto> students = controller.getStudentsByGenerator(generator);
        for (StudentDto student : students) {
            JPanel studentLine = createStudentLine(student);
            studentLine.setOpaque(false);
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

    @Override
    public void onTableUpdate(String updateStatus) {
        // no operation
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        // no operation
    }


    public void setGenerators(List<GeneratorType> generatorTypes) {
        activeGenerators = generatorTypes;
        updateStudentList();
    }

    private void updateStudentList() {
        contentPanel.removeAll();
        for (GeneratorType generator : activeGenerators) {
            JPanel studentListPanel = createStudentListPanel(generator);
            contentPanel.add(studentListPanel);
            contentPanel.add(Box.createHorizontalStrut(smallGap));
        }
    }

    private JPanel createStudentLine(StudentDto student) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);

        JLabel nameLabel = new CustomLabel(student.fullName());
        nameLabel.setOpaque(false);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();

        // Имя студента — первый столбец
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, smallGap); // отступ справа от имени
        panel.add(nameLabel, gbc);

        // Чекбокс — второй столбец
        gbc.gridx = 1;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);
        panel.add(checkBox, gbc);

        gbc.gridx = 2;
        panel.add(Box.createHorizontalStrut(10), gbc);


        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);


        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap / 2));

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(ButtonCommand.GENERATE_ALL.getName()));
        panel.add(Box.createHorizontalStrut(2 * mediumGap));
        panel.add(createButton(ButtonCommand.GENERATE.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }
}
