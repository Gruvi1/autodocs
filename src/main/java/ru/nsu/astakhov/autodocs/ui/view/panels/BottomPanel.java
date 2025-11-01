package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;

@Scope("prototype")
@Component
public class BottomPanel extends Panel {
    public BottomPanel(ButtonEventHandler buttonEventHandler) {
        super(buttonEventHandler);
    }

    @Override
    public void configurePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        Color backgroundColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.BACKGROUND_COLOR));
        Color primaryColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.PRIMARY_COLOR));
        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        setBackground(primaryColor);


//        setBorder(BorderFactory.createLineBorder(primaryColor, 2));
        setBorder(BorderFactory.createMatteBorder(2, 0, 0, 0, focusColor));

        int gap = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.GAP_SMALL));
        add(Box.createHorizontalGlue());
        add(createLabel("qweqweqwe"));
        add(Box.createHorizontalStrut(gap));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);

        int textSize = Integer.parseInt(ConfigManager.getSetting(ConfigConstants.MENU_SIZE));
        label.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));

        Color textColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.TEXT_COLOR));

        label.setForeground(textColor);

        return label;
    }
}
