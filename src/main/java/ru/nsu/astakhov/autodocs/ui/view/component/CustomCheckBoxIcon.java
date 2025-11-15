package ru.nsu.astakhov.autodocs.ui.view.component;

import javax.swing.*;
import java.awt.*;

public class CustomCheckBoxIcon implements Icon {
    private final int size;

    public CustomCheckBoxIcon(int size) {
        this.size = size;
    }

    @Override
    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Рисуем квадрат
        g2d.setColor(Color.WHITE);
        g2d.fillRect(x, y, size - 1, size - 1);
        g2d.setColor(Color.GRAY);
        g2d.drawRect(x, y, size - 1, size - 1);

        // Если чекбокс выбран — рисуем галочку
        if (c instanceof JCheckBox && ((JCheckBox) c).isSelected()) {
            g2d.setColor(Color.BLUE);
            g2d.setStroke(new BasicStroke(2));
            g2d.drawLine(x + 4, y + size / 2, x + size / 2 - 2, y + size - 6);
            g2d.drawLine(x + size / 2 - 2, y + size - 6, x + size - 6, y + 4);
        }

        g2d.dispose();
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }
}