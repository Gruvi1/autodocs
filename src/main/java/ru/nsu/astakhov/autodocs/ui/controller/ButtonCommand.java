package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.Getter;

@Getter
public enum ButtonCommand {
    APPLICATION_TEMPLATES("Шаблоны заявлений"),
    CREATE_APPLICATION_TEMPLATE("Создать шаблон заявления"),
    ALL_DOC("Все документы"),
    INTERNSHIP_APPLICATION("Заявление на практику"),
    INDIVIDUAL_ASSIGNMENT("Индивидуальное задание"),
    INTERNSHIP_REPORT("Отчёт о практике"),
    REVIEW("Отзыв"),
    REVIEWER_COMMENT("Рецензия"),
    THESIS_SUPERVISOR_REVIEW("Отзыв руководителя ВКР");

    private final String name;

    ButtonCommand(String name) {
        this.name = name;
    }

    static ButtonCommand fromString(String commandName) {
        for (ButtonCommand command : ButtonCommand.values()) {
            if (command.getName().equals(commandName)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No such command: " + commandName);
    }
}
