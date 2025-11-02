package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class CollisionDialog extends JDialog {
    private static final String dialogName = "Конфликт данных";
    private final FieldCollision collision;
    private String selectedOption = null;

    public CollisionDialog(Frame owner, FieldCollision collision) {
        super(owner, dialogName, true);
        this.collision = collision;

        configureDialog();
        createOverlay(owner);
    }

    public static String showCollisionDialog(Frame owner, FieldCollision collision) {
        CollisionDialog dialog = new CollisionDialog(owner, collision);

        dialog.setVisible(true);

        return dialog.selectedOption;
    }

    private void configureDialog() {
        setResizable(false);

//        int width = 800;
//        int height = 250;
//        setSize(width, height);

        addWindowListener(new WindowAdapter() {
            @Override
                public void windowClosed(WindowEvent e) {
                selectedOption = null;
                removeOverlay((Frame) getOwner());
            }

            @Override
            public void windowClosing(WindowEvent e) {
                selectedOption = null;
                removeOverlay((Frame) getOwner());
            }
        });

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
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

        List<Component> components = new ArrayList<>(List.of(
                createTitleMessage(),
                Box.createVerticalStrut(25),
                createTextLabel(message),
                Box.createVerticalStrut(10)
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
        panel.add(Box.createHorizontalStrut(10));
        panel.add(createButton(thesisValue));
        panel.add(Box.createHorizontalGlue());

        return panel;
    }


    private JPanel createTitleMessage() {
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        firstLine.setOpaque(false);

        firstLine.add(createTextLabel("Студент "));
        firstLine.add(createBorderedLabel(collision.studentName()));
        firstLine.add(createTextLabel(" имеет разные значения поля "));

        JPanel secondLine = new JPanel();
        secondLine.setLayout(new BoxLayout(secondLine, BoxLayout.X_AXIS));
        secondLine.setOpaque(false);

        secondLine.add(createBorderedLabel(collision.fieldName()));
        secondLine.add(createTextLabel(" на листах практики и ВКР"));

        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setOpaque(false);

        column.add(firstLine);
        column.add(Box.createVerticalStrut(5));
        column.add(secondLine);

        return column;
    }














    private JLabel createBorderedLabel(String text) {
        return createCustomLabel(text, true);
    }

    private JLabel createTextLabel(String text) {
        return createCustomLabel(text, false);
    }

    private JLabel createCustomLabel(String text, boolean opaque) {
        JLabel label = new JLabel(text);

        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));

        label.setBackground(backgroundColor);

        label.setOpaque(opaque);
        if (opaque) {
            label.setBorder(BorderFactory.createLineBorder(backgroundColor, 5));
        }
        label.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        label.setForeground(textColor);

        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setHorizontalAlignment(SwingConstants.CENTER);

        return label;
    }

    private JButton createButton(String buttonName) {
//        JButton button = new JButton(buttonName);
        RoundedButton button = new RoundedButton(buttonName, 5);
//        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
//        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));
        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
//
        button.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
//        button.setBackground(backgroundColor);
//        button.setForeground(textColor);
//        button.setBorderPainted(false);
//        button.setFocusPainted(false);
//
//        button.setAlignmentX(Component.CENTER_ALIGNMENT);
//        button.setHorizontalAlignment(SwingConstants.CENTER);
//
//        button.setActionCommand(buttonName);
        button.addActionListener(e -> {
            selectedOption = buttonName;
            setVisible(false);
            dispose();
        });

        return button;
    }





















    private void createOverlay(Frame owner) {
        if (owner == null) {
            return;
        }
        JPanel overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 64));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        overlay.setOpaque(false);
        JRootPane rootPane = SwingUtilities.getRootPane(owner);
        if (rootPane != null) {
            rootPane.setGlassPane(overlay);
            overlay.setVisible(true);
        }
    }

    private void removeOverlay(Frame owner) {
        if (owner == null) {
            return;
        }
        JRootPane rootPane = SwingUtilities.getRootPane(owner);
        if (rootPane != null) {
            rootPane.getGlassPane().setVisible(false);
        }
    }
}