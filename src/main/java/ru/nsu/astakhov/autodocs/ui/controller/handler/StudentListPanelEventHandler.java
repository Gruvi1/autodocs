package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.document.PreparedTemplateInfo;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panel.StudentListPanel;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

@RequiredArgsConstructor
public class StudentListPanelEventHandler implements EventHandler {
    private final Controller controller;
    private final StudentListPanel panel;

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        ButtonCommand buttonCommand = ButtonCommand.fromString(command);

        switch (buttonCommand) {
            case GENERATE_ALL -> {
                for (PreparedTemplateInfo preparedTemplateInfo : panel.getActiveGenerators()) {
                    controller.generateStudents(
                            JOptionPane.getFrameForComponent(panel), preparedTemplateInfo, panel.getAllStudents(preparedTemplateInfo)
                    );
                }
            }
            case GENERATE_SELECTED -> {
                for (PreparedTemplateInfo preparedTemplateInfo : panel.getActiveGenerators()) {
                    controller.generateStudents(
                            JOptionPane.getFrameForComponent(panel), preparedTemplateInfo, panel.getSelectedStudents(preparedTemplateInfo)
                    );
                }
            }
        }
    }
}
