package ru.nsu.astakhov.autodocs.ui.view.component;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectableLabel extends JPanel  {
    private final String text;
    private final JCheckBox checkBox;
    @Getter
    private boolean isActive;

    public SelectableLabel(String text) {
        this.text = text;

        this.isActive = false;
        this.checkBox = createCheckBox();
        configureSelectableLabel();
    }

    public void setActive(boolean active) {
        this.isActive = active;
        this.checkBox.setSelected(active);
    }

    private void configureSelectableLabel() {
        setLayout(new BorderLayout());
        setOpaque(false);

        setBorder(BorderFactory.createEmptyBorder());

        add(new CustomLabel(text), BorderLayout.CENTER);
        add(checkBox, BorderLayout.EAST);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeActive();
            }
        });
    }

    private JCheckBox createCheckBox() {
        JCheckBox checkBox = new JCheckBox();
        checkBox.setEnabled(false);
        checkBox.setOpaque(false);

        checkBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeActive();
            }
        });

        return checkBox;
    }

    private void changeActive() {
        isActive = !isActive;

        checkBox.setSelected(isActive);
    }
}
