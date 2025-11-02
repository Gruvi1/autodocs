package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.model.FieldCollision;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.Observable;
import ru.nsu.astakhov.autodocs.ui.view.panels.CollisionDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Controller implements Observable {
    private final List<Listener> listeners;
    private final StudentService studentService;

    public Controller(StudentService studentService) {
        this.listeners = new ArrayList<>();
        this.studentService = studentService;
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

    public void updateTable() {
        SwingWorker<List<FieldCollision>, String> worker = new SwingWorker<>() {
            @Override
            protected List<FieldCollision> doInBackground() {
                publish("Удаление старых данных...");
                studentService.clearAllStudents();

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
                        resolveCollisions(collisions);
                        notifyAllTableUpdate(successUpdateMessage);
                    }
                }
                catch (Exception e) {
                    e.printStackTrace(); // TODO: исправить исключение
                    notifyAllTableUpdate("Ошибка при обновлении");
                }
            }
        };
        worker.execute();
    }

    public void testShowDialog(Frame owner) {
        CollisionDialog.showCollisionDialog(owner, null);
    }

    private void resolveCollisions(List<FieldCollision> collisions) {
        if (collisions.isEmpty()) {
            return;
        }

        for (FieldCollision collision : collisions) {
            String ans = CollisionDialog.showCollisionDialog(null, collision);
            collision.resolve(ans);
            System.out.println(ans);
        }

        studentService.saveResolvedField(collisions);
    }
}




