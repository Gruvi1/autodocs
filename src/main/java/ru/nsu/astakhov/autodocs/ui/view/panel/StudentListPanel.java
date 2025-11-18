package ru.nsu.astakhov.autodocs.ui.view.panel;

import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.StudentListPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.GeneratorCard;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class StudentListPanel extends Panel {
    private final transient Controller controller;
    @Getter
    private final Map<GeneratorType, GeneratorCard> activeGeneratorCardMap;
    private final JPanel contentPanel;

    public StudentListPanel(Controller controller) {
        this.controller = controller;

        controller.addListener(this);
        setEventHandler(new StudentListPanelEventHandler(controller,this));

        this.activeGeneratorCardMap = new HashMap<>();
        this.contentPanel = initContentPanel();

        configurePanel();
    }

    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        setBackground(backgroundColor);

        add(createStudentListsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }

    private JScrollPane createStudentListsPanel() {
        JScrollPane scrollPane = new JScrollPane(contentPanel);

        scrollPane.setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        JScrollBar verticalBar = scrollPane.getVerticalScrollBar();
        verticalBar.setUnitIncrement(smallGap);

        return scrollPane;
    }

    private JPanel initContentPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
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

    public void setGenerators(List<GeneratorType> generatorTypes) {
        activeGeneratorCardMap.clear();
        for (GeneratorType generatorType : generatorTypes) {
            activeGeneratorCardMap.put(generatorType, null);
        }
        updateStudentList();
    }

    public List<GeneratorType> getActiveGenerators() {
        return new ArrayList<>(activeGeneratorCardMap.keySet());
    }

    private void updateStudentList() {
        contentPanel.removeAll();

        contentPanel.add(Box.createHorizontalGlue());
        for (GeneratorType generator : activeGeneratorCardMap.keySet()) {
            GeneratorCard generatorCard = new GeneratorCard(generator, controller);
            activeGeneratorCardMap.put(generator, generatorCard);
            contentPanel.add(generatorCard);
            contentPanel.add(Box.createHorizontalStrut(smallGap));
        }
        contentPanel.add(Box.createHorizontalGlue());

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
        panel.add(createButton(ButtonCommand.GENERATE.getName()));
        panel.add(Box.createHorizontalStrut(mediumGap));

        return panel;
    }

    public List<StudentDto> getAllStudents(GeneratorType generatorType) {
        GeneratorCard generatorCard = activeGeneratorCardMap.get(generatorType);
        if (generatorCard != null) {
            return generatorCard.getAllStudents();
        }
        return new ArrayList<>();
    }

    public List<StudentDto> getSelectedStudents(GeneratorType generatorType) {
        GeneratorCard generatorCard = activeGeneratorCardMap.get(generatorType);
        if (generatorCard != null) {
            return generatorCard.getSelectedStudents();
        }
        return new ArrayList<>();
    }
}