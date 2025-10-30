package ru.nsu.astakhov.autodocs.ui.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.service.StudentService;

@RequiredArgsConstructor
@Service
@Slf4j
public class Controller {
    private final StudentService studentService;

    public void updateTable() {
        studentService.clearAllStudents();
        studentService.scanAllData();
    }
}
