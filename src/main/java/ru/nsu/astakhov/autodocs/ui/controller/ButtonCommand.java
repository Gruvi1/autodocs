package ru.nsu.astakhov.autodocs.ui.controller;

public enum ButtonCommand {
    APPLICATION_TEMPLATES("Шаблоны заявлений"),
    CREATE_APPLICATION_TEMPLATE("Создать шаблон заявления"),
    THREE("Three"),
    ALL_DOC("Все документы"),
    INTERNSHIP_APPLICATION("Заявление на практику"),
    INDIVIDUAL_ASSIGNMENT("Индивидуальное задание"),
    INTERNSHIP_REPORT("Отчёт о практике"),
    REVIEW("Отзыв"),
    REVIEWER_COMMENT("Рецензия"),
    THESIS_SUPERVISOR_REVIEW("Отзыв руководителя ВКР");


    private final String command;

    ButtonCommand(String command) {
        this.command = command;
    }

    public String getString() {
        return command;
    }

    static ButtonCommand fromString(String strCommand) {
        for (ButtonCommand command : ButtonCommand.values()) {
            if (command.getString().equals(strCommand)) {
                return command;
            }
        }
        throw new IllegalArgumentException("No such command: " + strCommand);
    }

}
