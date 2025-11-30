package ru.nsu.astakhov.autodocs.ui.view.dialog;

import com.github.petrovich4j.Gender;
import ru.nsu.astakhov.autodocs.ui.controller.GenderConflict;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GenderConflictDialog extends Dialog {
    private static final String DIALOG_NAME = "Выбор пола студента";
    private static final String MALE_GENDER = "Мужской";
    private static final String FEMALE_GENDER = "Женский";
    private final transient GenderConflict conflict;
    private final Frame owner;
    private String selectedGender = null;

    public GenderConflictDialog(Frame owner, GenderConflict conflict) {
        super(owner, DIALOG_NAME);
        this.owner = owner;
        this.conflict = conflict;

        configureDialog();
    }

    // TODO: возможно, вынести это в абстрактный класс
    // TODO: конфиг всегда такой
    // TODO: всегда будут 3 панели, а как их создавать - решать наследнику
    @Override
    protected void configureDialog() {
        setResizable(false);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));
        contentPanel.setBackground(focusColor);

        contentPanel.add(createCentralPanel(), BorderLayout.CENTER);
        contentPanel.add(createIconLabel(), BorderLayout.WEST);
        contentPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(contentPanel);
        pack();
        setLocationRelativeTo(getOwner());
    }

    public String showDialog() {
        createOverlay(owner);
        setVisible(true);
        removeOverlay(owner);

        return selectedGender;
    }

    private JPanel createCentralPanel() {
        String message = "Выберите пол студента:";

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new CustomLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        java.util.List<Component> components = new ArrayList<>(List.of(
                createTitleMessage(),
                Box.createVerticalStrut(mediumGap),
                label,
                Box.createVerticalStrut(smallGap)
        ));

        for (Component component : components) {
            panel.add(component);
        }

        return panel;
    }

    private JLabel createIconLabel() {
        String questionIconKey = "OptionPane.questionIcon";
        Icon questionIcon = UIManager.getIcon(questionIconKey);

        return new JLabel(questionIcon);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(MALE_GENDER));
        panel.add(Box.createHorizontalStrut(smallGap));
        panel.add(createButton(FEMALE_GENDER));
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JPanel createTitleMessage() {
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        firstLine.setOpaque(false);
        firstLine.add(new CustomLabel("Не удалось автоматически определить пол"));

        JPanel secondLine = new JPanel();
        secondLine.setLayout(new BoxLayout(secondLine, BoxLayout.X_AXIS));
        secondLine.setOpaque(false);
        secondLine.add(new CustomLabel("для студента "));
        secondLine.add(new CustomLabel(conflict.getStudentName(), true));


        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setOpaque(false);
        column.add(firstLine);
        column.add(Box.createVerticalStrut(smallGap / 2));
        column.add(secondLine);

        return column;
    }

    private JButton createButton(String buttonName) {
        RoundedButton button = new RoundedButton(buttonName);
        button.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        button.addActionListener(e -> {
            selectedGender = buttonName.equals(MALE_GENDER)
                    ? Gender.Male.name()
                    : Gender.Female.name();
            setVisible(false);
            dispose();
        });

        return button;
    }
}