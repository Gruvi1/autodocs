package ru.nsu.astakhov.autodocs.ui.view.panel;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.model.WorkType;
import ru.nsu.astakhov.autodocs.model.TableWarning;
import ru.nsu.astakhov.autodocs.model.WarningList;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.WarningsPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class WarningsPanel extends Panel {
    private final transient WarningList warningList;
    private final JPanel lines;

    public WarningsPanel(WarningList warningList, Controller controller) {
        this.warningList = warningList;

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
            lines.add(Box.createVerticalStrut(smallGap));
        }
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        // no operation
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(lines);
        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(smallGap);

        setBackground(focusColor);

        scrollPane.setBackground(focusColor);

        lines.setBackground(focusColor);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createLine(TableWarning warning) {
        JPanel line = new JPanel();
        line.setLayout(new BoxLayout(line, BoxLayout.X_AXIS));
        line.setOpaque(false);

        String workType = warning.workType() == WorkType.INTERNSHIP ? "практики" : "ВКР";

        line.add(new CustomLabel("В таблице "));
        line.add(new CustomLabel(workType, true));
        line.add(new CustomLabel(" у студента "));
        line.add(new CustomLabel(warning.studentName(), true));
        line.add(new CustomLabel(" не определено поле: "));
        line.add(new CustomLabel(warning.fieldName(), true));

        line.setAlignmentX(LEFT_ALIGNMENT);

        return line;
    }
}