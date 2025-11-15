package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.StudentListPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomCheckBoxIcon;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.FileBox;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.List;


@Component
public class StudentListPanel extends Panel {
    private final Controller controller;
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
    public void configurePanel() {
        setLayout(new BorderLayout());

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        setBackground(backgroundColor);

        add(createStudentListsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }




    private JScrollPane createStudentListsPanel() {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

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

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap));
        panel.setBackground(backgroundColor);

        return panel;
    }

    private JPanel createStudentListPanel(GeneratorType generator) {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        int menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        panel.setBackground(focusColor);

        FileBox fileBox = new FileBox(generator);
        fileBox.setBorder(BorderFactory.createEmptyBorder());
        fileBox.setEnabled(false);
        fileBox.setOpaque(false);

        JButton generateAllButton = createButton(ButtonCommand.SELECT_ALL.getName());
        generateAllButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));

        panel.setAlignmentX(LEFT_ALIGNMENT);
        generateAllButton.setAlignmentX(LEFT_ALIGNMENT);



        panel.add(fileBox);
        panel.add(Box.createVerticalStrut(smallGap));
        panel.add(generateAllButton);
        panel.add(Box.createVerticalStrut(smallGap));
        panel.add(createStudentLinesPanel(generator));

        return panel;
    }

    private JScrollPane createStudentLinesPanel(GeneratorType generator) {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
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
    public void onTableUpdate(String updateStatus) {}

    @Override
    public void onDocumentGeneration(String generateStatus) {}

    public void setGenerators(List<GeneratorType> generatorTypes) {
        activeGenerators = generatorTypes;
        updateStudentList();
    }

    private void updateStudentList() {
        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

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

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        JLabel nameLabel = new CustomLabel(student.fullName());
        nameLabel.setOpaque(false);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setOpaque(false);

//        JCheckBox checkBox = new JCheckBox();
//        checkBox.setOpaque(false);
//        checkBox.setIcon(new CustomCheckBoxIcon(25));          // пустой
//        checkBox.setSelectedIcon(new CustomCheckBoxIcon(25));   // с галочкой
//        checkBox.setFocusPainted(false);                        // убираем рамку фокуса

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

        int smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        int mediumGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_MEDIUM));
        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));

        panel.setBorder(BorderFactory.createLineBorder(backgroundColor, smallGap / 2));

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(ButtonCommand.GENERATE_ALL.getName()));
        panel.add(Box.createHorizontalStrut(2 * mediumGap));
        panel.add(createButton(ButtonCommand.GENERATE.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }
}
