package ru.nsu.astakhov.autodocs.ui.view.panel;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.PreparedTemplateInfo;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.StudentListPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.GeneratorCard;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class StudentListPanel extends Panel {
    private final transient Controller controller;
    @Getter
    private final Map<PreparedTemplateInfo, GeneratorCard> activeGeneratorCardMap;
    private final JPanel contentPanel;

    public StudentListPanel(Controller controller) {
        this.controller = controller;

        controller.addListener(this);
        setEventHandler(new StudentListPanelEventHandler(controller, this));

        this.activeGeneratorCardMap = new HashMap<>();
        this.contentPanel = initContentPanel();

        configurePanel();
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        add(contentPanel, BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }

    private JPanel initContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, smallGap, smallGap));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        return panel;
    }

    @Override
    public void onTableUpdate(String updateStatus) {
        // no operation
    }

    @Override
    public void onDocumentGeneration(String generateStatus) {
        // no operation
    }

    public void setGenerators(List<PreparedTemplateInfo> templatesInfo) {
        activeGeneratorCardMap.clear();
        for (PreparedTemplateInfo preparedTemplateInfo : templatesInfo) {
            activeGeneratorCardMap.put(preparedTemplateInfo, null);
        }
        updateStudentList();
    }

    public List<PreparedTemplateInfo> getActiveGenerators() {
        return new ArrayList<>(activeGeneratorCardMap.keySet());
    }

    private void updateStudentList() {
        contentPanel.removeAll();

        for (PreparedTemplateInfo preparedTemplateInfo : activeGeneratorCardMap.keySet()) {
            GeneratorCard generatorCard = new GeneratorCard(preparedTemplateInfo, controller);
            activeGeneratorCardMap.put(preparedTemplateInfo, generatorCard);
            contentPanel.add(generatorCard);
        }

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    private JPanel createButtonsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(backgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        panel.add(Box.createHorizontalGlue());
        panel.add(createButton(ButtonCommand.GENERATE_ALL.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));
        panel.add(createButton(ButtonCommand.GENERATE_SELECTED.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }

    public List<StudentDto> getAllStudents(PreparedTemplateInfo preparedTemplateInfo) {
        GeneratorCard generatorCard = activeGeneratorCardMap.get(preparedTemplateInfo);
        if (generatorCard != null) {
            return generatorCard.getAllStudents();
        }
        return new ArrayList<>();
    }

    public List<StudentDto> getSelectedStudents(PreparedTemplateInfo preparedTemplateInfo) {
        GeneratorCard generatorCard = activeGeneratorCardMap.get(preparedTemplateInfo);
        if (generatorCard != null) {
            return generatorCard.getSelectedStudents();
        }
        return new ArrayList<>();
    }
}