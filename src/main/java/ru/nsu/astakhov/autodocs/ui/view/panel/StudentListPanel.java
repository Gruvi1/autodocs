package ru.nsu.astakhov.autodocs.ui.view.panel;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.StudentListPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.GeneratorCard;

import javax.swing.*;
import java.awt.*;
import java.util.List;


@Component
public class StudentListPanel extends Panel {
    private final transient Controller controller;
    private List<GeneratorType> activeGenerators;
    private final JPanel contentPanel;

    public StudentListPanel(Controller controller) {
        this.controller = controller;

        contentPanel = initContentPanel();

        controller.addListener(this);
        setEventHandler(new StudentListPanelEventHandler(controller,this));

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
        activeGenerators = generatorTypes;
        updateStudentList();
    }

    private void updateStudentList() {
        contentPanel.removeAll();

        contentPanel.add(Box.createHorizontalGlue());
        for (GeneratorType generator : activeGenerators) {
            contentPanel.add(new GeneratorCard(generator, controller, getEventHandler()));
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
}