package ru.nsu.astakhov.autodocs.core.repositories;

import jakarta.persistence.Embeddable;

//@Embeddable
//public record Supervisor (
//        String name, // фио
//        String position, // должность
//        String degree, // учёная степень
//        String title // учёное звание
//) {
//}



@Embeddable
public class Supervisor {
    private String name; // фио
    private String position; // должность
    private String degree; // учёная степень
    private String title; // учёное звание

    // Обязательно: пустой конструктор (для Hibernate)
    public Supervisor() {}

    // Конструктор, геттеры, сеттеры
    public Supervisor(String name, String position, String degree, String title) {
        this.name = name;
        this.position = position;
        this.degree = degree;
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
