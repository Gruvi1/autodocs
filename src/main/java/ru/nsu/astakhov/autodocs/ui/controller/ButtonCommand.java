package ru.nsu.astakhov.autodocs.ui.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ButtonCommand {
    UPDATE_TABLE("Обновить таблицу"),
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

    public static ButtonCommand fromString(String strCommand) {
        for (ButtonCommand command : ButtonCommand.values()) {
            if (command.getName().equals(strCommand)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No such button command: " + strCommand);
    }
}
