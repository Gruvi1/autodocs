package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.Listener;
import ru.nsu.astakhov.autodocs.ui.Observable;

import javax.swing.*;
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
        SwingWorker<Void, String> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                publish("Удаление старых данных");
                studentService.clearAllStudents();

                publish("Обновление таблицы");
                studentService.scanAllData();

                return null;
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
                notifyAllTableUpdate("Обновление завершено");
            }
        };
        worker.execute();
    }
}




