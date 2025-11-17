package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.EventHandler;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GeneratorCard extends JPanel {
    private final GeneratorType generator;
    private final Controller controller;
    private final transient EventHandler eventHandler;

    private final int smallGap;

    private final int menuTextSize;

    private final Color primaryColor;
    private final Color focusColor;

    public GeneratorCard(GeneratorType generator, Controller controller, EventHandler eventHandler) {
        this.generator = generator;
        this.controller = controller;
        this.eventHandler = eventHandler;

        this.smallGap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));

        this.menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));

        configureCard();
    }
    
    private void configureCard() {
        setLayout(new BorderLayout());
        setBackground(focusColor);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(primaryColor),
                BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap)
        ));
        setMaximumSize(new Dimension(600, Integer.MAX_VALUE));

        add(createTitlePanel(), BorderLayout.NORTH);
        add(createSelectAllButton(), BorderLayout.CENTER);
        add(createStudentLinesPanel(generator), BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        FileBox fileBox = new FileBox(generator);
        fileBox.setBorder(BorderFactory.createEmptyBorder());
        fileBox.setEnabled(false);

        return fileBox;
    }

    private JButton createSelectAllButton() {
        String buttonName = ButtonCommand.SELECT_ALL.getName();

        RoundedButton generateAllButton = new RoundedButton(buttonName, 0);
        generateAllButton.setActionCommand(buttonName);
        generateAllButton.addActionListener(eventHandler);
        generateAllButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        generateAllButton.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        return generateAllButton;
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

        JLabel nameLabel = new CustomLabel(student.fullName());
        nameLabel.setOpaque(false);

        JCheckBox checkBox = new JCheckBox();
        checkBox.setOpaque(false);

//        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(nameLabel);
        panel.add(Box.createHorizontalGlue());
        panel.add(checkBox);
//        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }
}
