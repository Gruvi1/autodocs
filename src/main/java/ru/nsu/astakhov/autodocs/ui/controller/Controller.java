package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.Observable;
import ru.nsu.astakhov.autodocs.ui.view.panels.PanelManager;
import ru.nsu.astakhov.autodocs.ui.view.panels.CollisionDialog;
import ru.nsu.astakhov.autodocs.ui.view.panels.Panel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
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
    public void removeListener(Listener l) {
        listeners.remove(l);
    }

    @Override
    public void notifyAllTableUpdate(String updateStatus) {
        for (Listener l : listeners) {
            l.onTableUpdate(updateStatus);
        }
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
            protected void process(java.util.List<String> chunks) {
                // Вызывается в EDT
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
                        resolveCollisions(collisions, owner);
                        notifyAllTableUpdate(successUpdateMessage);
                    }
                }
                catch (Exception e) {
                    // TODO: исправить исключение
                    notifyAllTableUpdate("Ошибка при обновлении");
                }
            }
        };
        worker.execute();
    }

    public void createIndWorkDoc() {
        studentService.createIndWorkDoc();
    }

    public <P extends Panel> void setPanel(Class<P> requiredType) {
        panelManager.setPanel(requiredType);
    }

    private void resolveCollisions(List<FieldCollision> collisions, Frame owner) {
        if (collisions.isEmpty()) {
            return;
        }

        for (FieldCollision collision : collisions) {
            String ans = CollisionDialog.showCollisionDialog(owner, collision);
            collision.resolve(ans);
        }

        studentService.saveResolvedField(collisions);
    }
}