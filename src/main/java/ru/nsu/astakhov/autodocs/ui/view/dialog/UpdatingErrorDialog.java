package ru.nsu.astakhov.autodocs.ui.view.dialog;

import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

public class UpdatingErrorDialog extends Dialog {
    private static final String DIALOG_NAME = "Ошибка обновления данных";
    private final Frame owner;

    public UpdatingErrorDialog(Frame owner) {
        super(owner, DIALOG_NAME);
        this.owner = owner;

        configureDialog();
    }

    public void showDialog() {
        createOverlay(owner);
        setVisible(true);
        removeOverlay(owner);
    }

    @Override
    protected void configureDialog() {
        setResizable(false);

        String message = "Пожалуйста, дождитесь завершения обновления данных";

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));
        contentPanel.setBackground(focusColor);

        contentPanel.add(new CustomLabel(message), BorderLayout.CENTER);
        contentPanel.add(createIconLabel(), BorderLayout.WEST);
        contentPanel.add(createButton(), BorderLayout.SOUTH);

        add(contentPanel);
        pack();
        setLocationRelativeTo(getOwner());
    }

    private JLabel createIconLabel() {
        String warningIconKey = "OptionPane.warningIcon";
        Icon questionIcon = UIManager.getIcon(warningIconKey);

        return new JLabel(questionIcon);
    }

    private JButton createButton() {
        String buttonName = "ОК";
        RoundedButton button = new RoundedButton(buttonName);
        button.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        button.addActionListener(e -> {
            setVisible(false);
            dispose();
        });

        return button;
    }
}
