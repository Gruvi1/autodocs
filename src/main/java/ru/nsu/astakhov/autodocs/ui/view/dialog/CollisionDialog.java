package ru.nsu.astakhov.autodocs.ui.view.dialog;

import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDialog extends Dialog {
    private static final String DIALOG_NAME = "Конфликт данных";
    private final transient FieldCollision collision;
    private String selectedOption = null;
    private final Frame owner;

    public CollisionDialog(Frame owner, FieldCollision collision) {
        super(owner, DIALOG_NAME);
        this.owner = owner;
        this.collision = collision;

        configureDialog();
    }

    public String showDialog() {
        createOverlay(owner);
        setVisible(true);
        removeOverlay(owner);

        return selectedOption;
    }

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

    private JPanel createCentralPanel() {
        String message = "Выберите, что сохранить:";

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel label = new CustomLabel(message);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        List<Component> components = new ArrayList<>(List.of(
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
        Icon questionIcon = UIManager.getIcon("OptionPane.questionIcon");

        return new JLabel(questionIcon);
    }

    private JPanel createButtonPanel() {
        String practiceValue = collision.practiceValue();
        String thesisValue = collision.thesisValue();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(practiceValue));
        panel.add(Box.createHorizontalStrut(smallGap));
        panel.add(createButton(thesisValue));
        panel.add(Box.createHorizontalGlue());

        return panel;
    }

    private JPanel createTitleMessage() {
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        firstLine.setOpaque(false);

        firstLine.add(new CustomLabel("Студент "));
        firstLine.add(new CustomLabel(collision.studentName(), true));
        firstLine.add(new CustomLabel(" имеет разные значения поля "));

        JPanel secondLine = new JPanel();
        secondLine.setLayout(new BoxLayout(secondLine, BoxLayout.X_AXIS));
        secondLine.setOpaque(false);

        secondLine.add(new CustomLabel(collision.fieldName(), true));
        secondLine.add(new CustomLabel(" на листах практики и ВКР"));

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
            selectedOption = buttonName;
            setVisible(false);
            dispose();
        });

        return button;
    }
}