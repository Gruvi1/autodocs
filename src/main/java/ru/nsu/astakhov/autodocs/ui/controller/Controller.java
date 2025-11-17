package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.document.GeneratorType;
import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.Observable;
import ru.nsu.astakhov.autodocs.ui.view.dialog.CollisionDialog;
import ru.nsu.astakhov.autodocs.ui.view.panels.PanelManager;
import ru.nsu.astakhov.autodocs.ui.view.panels.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Slf4j
@Component
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

    public List<StudentDto> getStudentsByGenerator(GeneratorType generatorType) {
        return studentService.getStudentsByGenerator(generatorType);
    }

    public List<StudentDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    public void generateAllStudents(List<GeneratorType> generatorTypes) {
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                publish("Генерация документов...");
                studentService.generateAllStudents(generatorTypes);
                return null;
            }

            @Override
            protected void process(List<String> chunks) {
                if (!chunks.isEmpty()) {
                    notifyAllDocumentGeneration(chunks.getLast());
                }
            }

            @Override
            protected void done() {
                notifyAllDocumentGeneration("Генерация завершена!");
            }
        };
        worker.execute();
    }

    public void updateTable(Frame owner) {
        SwingWorker<List<FieldCollision>, String> worker = new SwingWorker<>() {
            @Override
            protected List<FieldCollision> doInBackground() {
                publish("Удаление старых данных...");
                studentService.clearAllData();

                publish("Получение данных из таблиц практики...");
                studentService.scanInternshipLists();

                publish("Получение данных из таблиц ВКР...");
                List<FieldCollision> collisions = studentService.scanThesisLists();

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
                    List<FieldCollision> collisions = get();
                    if (collisions.isEmpty()) {
                        notifyAllTableUpdate(successUpdateMessage);
                    }
                    else {
                        resolveCollisions(owner, collisions);
                        notifyAllTableUpdate(successUpdateMessage);
                    }
                }
                catch (InterruptedException e) {
                    notifyAllTableUpdate("Ошибка при обновлении");
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException e) {
                    notifyAllTableUpdate("Ошибка при обновлении");
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

    private void resolveCollisions(Frame owner, List<FieldCollision> collisions) {
        if (collisions.isEmpty()) {
            return;
        }

        for (FieldCollision collision : collisions) {
            String answer = new CollisionDialog(owner, collision).showDialog();

            collision.resolve(answer);
        }

        studentService.saveResolvedField(collisions);
    }
}