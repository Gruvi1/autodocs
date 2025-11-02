package ru.nsu.astakhov.autodocs.ui.view.panels;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.model.TableType;
import ru.nsu.astakhov.autodocs.model.TableWarning;
import ru.nsu.astakhov.autodocs.model.WarningList;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigConstants;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.WarningsPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class WarningsPanel extends Panel implements Listener {
    private final WarningList warningList;
    private final Controller controller;
    private final JPanel lines;

    public WarningsPanel(WarningList warningList, Controller controller) {
        this.warningList = warningList;
        this.controller = controller;

        lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));

        controller.addListener(this);
        setEventHandler(new WarningsPanelEventHandler(controller,this));

        configurePanel();
    }

    @Override
    public void onTableUpdate(String updateStatus) {
        List<TableWarning> warnings = warningList.getWarnings();

        lines.removeAll();

        for (TableWarning warning : warnings) {
            lines.add(createLine(warning));
            lines.add(Box.createVerticalStrut(10));
        }
    }

    @Override
    public void configurePanel() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(lines);

        Color focusColor = ConfigManager.parseHexColor(ConfigManager.getSetting(ConfigConstants.FOCUS_COLOR));
        setBackground(focusColor);
        scrollPane.getViewport().setBackground(Color.BLUE);

        scrollPane.setBackground(focusColor);

        lines.setBackground(focusColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(scrollPane);
    }

    private JPanel createLine(TableWarning warning) {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);

        // TODO: возможно добавить параметр в перечисление, чтобы не мудохаться каждый раз
        String tableType = warning.tableType() == TableType.INTERNSHIP ? "практики" : "ВКР";

        line.add(createTextLabel("В таблице "));
        line.add(createBorderedLabel(tableType));
        line.add(createTextLabel(" у студента "));
        line.add(createBorderedLabel(warning.studentName()));
        line.add(createTextLabel(" не определено поле: "));
        line.add(createBorderedLabel(warning.fieldName()));

        line.setAlignmentX(LEFT_ALIGNMENT);

        return line;
    }


    // TODO: ДУБЛИРУЕТСЯ В CollisionDialog И WarningsPanel
    private JLabel createBorderedLabel(String text) {
        return createCustomLabel(text, true);
    }

    // TODO: ДУБЛИРУЕТСЯ В CollisionDialog И WarningsPanel
    private JLabel createTextLabel(String text) {
        return createCustomLabel(text, false);
    }

    // TODO: ДУБЛИРУЕТСЯ В CollisionDialog И WarningsPanel
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

        return label;
    }
}