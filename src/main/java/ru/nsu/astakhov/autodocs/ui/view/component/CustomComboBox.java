package ru.nsu.astakhov.autodocs.ui.view.component;

import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class CustomComboBox extends JComboBox<String> {
    private final int menuTextSize;

    private final Color primaryColor;
    private final Color backgroundColor;
    private final Color focusColor;
    private final Color textColor;
    
    public CustomComboBox(String[] parameters) {
        this.menuTextSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));

        this.primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        this.backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        this.focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        this.textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        String placeholder = "–";
        String[] items = new String[parameters.length + 1];
        items[0] = placeholder;
        System.arraycopy(parameters, 0, items, 1, parameters.length);


        setModel(new DefaultComboBoxModel<>(items));
        setSelectedIndex(0); // выбираем placeholder

        configureComboBox();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRect(0, 0, getWidth(), getHeight());

        g2.setColor(primaryColor);
        g2.setStroke(new BasicStroke(2.0f));
        g2.drawRect(0, 0, getWidth() - 1, getHeight() - 1);


        int padding = 5;

        Object selectedItem = getSelectedItem();
        if (selectedItem != null) {
            String text = selectedItem.toString();
            FontMetrics fm = g2.getFontMetrics();
            int x = 5 + padding;
            int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent() / 2;
            g2.setColor(getForeground());
            g2.drawString(text, x, y);
        }

        g2.dispose();
    }

    private void configureComboBox() {
        setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        setForeground(textColor);
        setBackground(backgroundColor);

        Dimension dimension = getPreferredSize();
        dimension.setSize(dimension.getWidth(), dimension.getHeight() + 5);

        setPreferredSize(dimension);

        setBorder(BorderFactory.createEmptyBorder());
        setFocusable(false);


        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setForeground(textColor);
                if (isSelected) {
                    label.setBorder(BorderFactory.createLineBorder(focusColor, 5));
                    label.setBackground(focusColor);
                }
                else {
                    label.setBorder(BorderFactory.createLineBorder(backgroundColor, 5));
                    label.setBackground(backgroundColor);
                }
                return label;
            }
        });
        setUI(new CustomComboBoxUI());
    }

    private static class CustomComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton button = new JButton();
            button.setVisible(false);
            return button;
        }

        @Override
        public void paint(Graphics g, JComponent c) {
            if (c instanceof CustomComboBox customComboBox) {
                customComboBox.paintComponent(g);
            }
        }

        @Override
        protected ComboPopup createPopup() {
            Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));

            BasicComboPopup popup = (BasicComboPopup) super.createPopup();
            popup.setBorder(BorderFactory.createLineBorder(primaryColor, 2));
            return popup;
        }
    }
}