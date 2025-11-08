package ru.nsu.astakhov.autodocs.ui.view.panels;

import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CollisionDialog extends JDialog {
    private static final String DIALOG_NAME = "Конфликт данных";
    private final FieldCollision collision;
    private String selectedOption = null;
    private Component originalGlassPane;
    private JPanel overlay;

    private CollisionDialog(Frame owner, FieldCollision collision) {
        super(owner, DIALOG_NAME, true);
        this.collision = collision;

        configureDialog();
    }

    public static String showCollisionDialog(Frame owner, FieldCollision collision) {
        CollisionDialog dialog = new CollisionDialog(owner, collision);

        dialog.createOverlay(owner);
        dialog.setVisible(true);
        dialog.removeOverlay(owner);

        return dialog.selectedOption;
    }

    private void configureDialog() {
        setResizable(false);

//        int width = 800;
//        int height = 250;
//        setSize(width, height);

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
                new CustomLabel(message),
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
        column.add(Box.createVerticalStrut(5));
        column.add(secondLine);

        return column;
    }

    private JButton createButton(String buttonName) {
        RoundedButton button = new RoundedButton(buttonName);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        button.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));

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

        JRootPane rootPane = SwingUtilities.getRootPane(owner);
        if (rootPane == null) {
            return;
        }

        originalGlassPane = rootPane.getGlassPane();

        overlay = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(new Color(0, 0, 0, 64));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        overlay.setOpaque(false);
        rootPane.setGlassPane(overlay);
        overlay.setVisible(true);
    }

    private void removeOverlay(Frame owner) {
        if (owner == null || overlay == null) {
            return;
        }
        overlay.setVisible(false);

        JRootPane rootPane = SwingUtilities.getRootPane(owner);

        if (rootPane != null) {
            rootPane.setGlassPane(originalGlassPane);
            rootPane.getGlassPane().setVisible(false);
        }

        overlay = null;
        originalGlassPane = null;
    }
}