package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.Observable;
import ru.nsu.astakhov.autodocs.ui.view.panel.PanelManager;
import ru.nsu.astakhov.autodocs.ui.view.panel.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
// TODO: глобальная проблема
// TODO: контроллер содержит бизнес-логику, что неправильно с точки зрения архитектуры
public class Controller implements Observable {
    private final List<Listener> listeners;
    private final StudentService studentService;
    private final PanelManager panelManager;

    public Controller(StudentService studentService, PanelManager panelManager) {
        this.studentService = studentService;
        this.panelManager = panelManager;
        this.listeners = new ArrayList<>();
    }

    @Override
    public void addListener(Listener l) {
        listeners.add(l);
    }

    @Override
    public void notifyAllTableUpdate(String updateStatus) {
        for (Listener l : listeners) {
            l.onTableUpdate(updateStatus);
        }
    }

    @Override
    public void notifyAllDocumentGeneration(String generateStatus) {
        for (Listener l : listeners) {
            l.onDocumentGeneration(generateStatus);
        }
    }

//    public Gender resolveGenderProblem(Frame owner, )

    public List<StudentDto> getStudentsByGenerator(GeneratorType generatorType) {
        return studentService.getStudentsByGenerator(generatorType);
    }

    public void generateStudents(Frame owner, GeneratorType generatorType, List<StudentDto> studentDtos) {
        SwingWorker<List<GenderConflict>, String> worker = new SwingWorker<>() {
            @Override
            protected List<GenderConflict> doInBackground() {
                publish("Генерация документов...");
                List<GenderConflict> conflicts = studentService.generateStudents(studentDtos, generatorType);

                publish("Разрешение ошибок при генерации...");
                return conflicts;
            }

            @Override
            protected void process(List<String> chunks) {
                if (!chunks.isEmpty()) {
                    notifyAllDocumentGeneration(chunks.getLast());
                }
            }

            @Override
            protected void done() {
                String successGenerateMessage = "Генерация завершена!";
                String failedGenerateMessage = "Ошибка при генерации!";
                try {
                    List<GenderConflict> conflicts = get();
                    if (!conflicts.isEmpty()) {
                        logger.info("Resolving {} conflicts", conflicts.size());
                        resolveConflicts(owner, conflicts);
                    }
                    logger.info("Finishing generate");
                    List<StudentEntity> savedEntities = studentService.saveConflictingEntities(conflicts);
                    notifyAllDocumentGeneration("Обновленные данные сохранены");
                    List<StudentDto> savedDtos = StudentMapper.listToDto(savedEntities);
                    studentService.generateStudents(savedDtos, generatorType);
                    notifyAllDocumentGeneration(successGenerateMessage);
                }
                catch (InterruptedException e) {
                    logger.error("Generate interrupted", e);
                    Thread.currentThread().interrupt();
                    notifyAllDocumentGeneration(failedGenerateMessage);
                }
                catch (ExecutionException e) {
                    logger.error("Error during generate", e);
                    notifyAllDocumentGeneration(failedGenerateMessage);
                }
            }
        };
        worker.execute();
    }

    public void updateTable(Frame owner) {
        SwingWorker<List<FieldConflict>, String> worker = new SwingWorker<>() {
            @Override
            protected List<FieldConflict> doInBackground() {
                publish("Обновление данных студентов...");
                logger.info("Starting data update");
                List<FieldConflict> collisions = studentService.startUpdate();

                publish("Разрешение конфликта данных...");
                return collisions;
            }

            @Override
            protected void process(List<String> chunks) {
                if (!chunks.isEmpty()) {
                    notifyAllTableUpdate(chunks.getLast());
                }
            }
            @Override
            protected void done() {
                String successUpdateMessage = "Обновление завершено!";
                try {
                    List<FieldConflict> collisions = get();
                    if (!collisions.isEmpty()) {
                        logger.info("Resolving {} collisions", collisions.size());
                        resolveConflicts(owner, collisions);
                    }
                    logger.info("Finishing data update");
                    studentService.finishUpdate(collisions);
                    notifyAllTableUpdate(successUpdateMessage);
                }
                catch (InterruptedException e) {
                    logger.error("Update interrupted", e);
                    Thread.currentThread().interrupt();
                    handleError();
                }
                catch (ExecutionException e) {
                    logger.error("Error during update", e);
                    handleError();
                }
            }
        };
        worker.execute();
    }

    public <P extends Panel> void setPanel(Class<P> requiredType) {
        panelManager.setPanel(requiredType);
    }

    public <P extends Panel> P getPanel(Class<P> requiredType) {
        return panelManager.getPanel(requiredType);
    }

    private void resolveConflicts(Frame owner, List<? extends Conflict> conflicts) {
        if (conflicts == null || conflicts.isEmpty()) {
            return;
        }

        for (Conflict conflict : conflicts) {
            conflict.resolveViaDialog(owner);
        }
    }


    // TODO: хуйня какая-то, переделать
    private void handleError() {
        try {
            studentService.finishUpdate(List.of());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            logger.error("Failed to unlock after error", e);
        }
        notifyAllTableUpdate("Ошибка при обновлении");
    }
}