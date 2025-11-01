package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.service.StudentService;

import javax.swing.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class Controller {
    private final StudentService studentService;


//    public void updateTable() {
//        studentService.clearAllStudents();
//        studentService.scanAllData();
//    }


    public void updateTable() {
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                studentService.clearAllStudents();
                studentService.scanAllData();
                return null;
            }

            @Override
            protected void done() {
                // Обнови таблицу в GUI здесь, если нужно
                // Например: tableModel.fireTableDataChanged();
            }
        };
        worker.execute();
    }
}




