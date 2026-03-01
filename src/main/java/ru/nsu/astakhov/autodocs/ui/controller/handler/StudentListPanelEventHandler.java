package ru.nsu.astakhov.autodocs.ui.controller.handler;

import lombok.RequiredArgsConstructor;
import ru.nsu.astakhov.autodocs.document.TemplateInfo;
import ru.nsu.astakhov.autodocs.ui.controller.ButtonCommand;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.panel.StudentListPanel;

import javax.swing.*;
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
                for (TemplateInfo templateInfo : panel.getActiveGenerators()) {
                    controller.generateStudents(
                            JOptionPane.getFrameForComponent(panel), templateInfo, panel.getAllStudents(templateInfo)
                    );
                }
            }
            case GENERATE_SELECTED -> {
                for (TemplateInfo templateInfo : panel.getActiveGenerators()) {
                    controller.generateStudents(
                            JOptionPane.getFrameForComponent(panel), templateInfo, panel.getSelectedStudents(templateInfo)
                    );
                }
            }
        }
    }
}
