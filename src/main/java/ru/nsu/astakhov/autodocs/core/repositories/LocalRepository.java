package ru.nsu.astakhov.autodocs.core.repositories;

import java.sql.*;

public class LocalRepository {
    private static final String DB_URL = "jdbc:sqlite:mydatabase.db";

    public static void main(String[] args) {
        createDatabaseAndTables();
//        insertSampleData();
//        readData();
    }

    public static void createDatabaseAndTables() {
        String createTableSQL = """
                CREATE TABLE IF NOT EXISTS students (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    full_name TEXT NOT NULL,
                    course INTEGER NOT NULL,
                    email TEXT NOT NULL,
                    phone_number TEXT,
                    edu_program TEXT NOT NULL,
                    group_name TEXT NOT NULL,
                    specialization TEXT NOT NULL,
                    order_on_approval_topic TEXT,
                    order_on_correction_topic TEXT,
                    actual_supervisor TEXT,
                    thesis_co_supervisor TEXT,
                    thesis_consultant TEXT,
                    thesis_topic TEXT,
                    reviewer TEXT,
                    internship_type TEXT,
                    thesis_supervisor_name TEXT,
                    thesis_supervisor_position TEXT,
                    thesis_supervisor_degree TEXT,
                    thesis_supervisor_title TEXT,
                    full_organization_name TEXT,
                    thesis_nsu_supervisor_name TEXT,
                    thesis_nsu_supervisor_position TEXT,
                    thesis_nsu_supervisor_degree TEXT,
                    thesis_nsu_supervisor_title TEXT,
                    thesis_organisation_supervisor_name TEXT,
                    thesis_organisation_supervisor_position TEXT,
                    thesis_organisation_supervisor_degree TEXT,
                    thesis_organisation_supervisor_title TEXT,
                    full_place_of_internship TEXT,
                    organization_name TEXT
                );
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableSQL);
            System.out.println("Таблица students создана или уже существует.");

        } catch (SQLException e) {
            System.out.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public static void insertSampleData() {
        String insertSQL = "INSERT INTO students (name, course, level) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            pstmt.setString(1, "qqqqqqqqqqqq");
            pstmt.setInt(2, 3);
            pstmt.setString(3, "бакалавр");
            pstmt.executeUpdate();

            pstmt.setString(1, "wwwwwwwwwwwwwwwwww");
            pstmt.setInt(2, 3);
            pstmt.setString(3, "бакалавр");
            pstmt.executeUpdate();

            System.out.println("Данные добавлены.");

        } catch (SQLException e) {
            System.out.println("Ошибка при вставке данных: " + e.getMessage());
        }
    }

    public static void readData() {
        String selectSQL = "SELECT * FROM students WHERE course = ? AND level = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {

            pstmt.setInt(1, 3);
            pstmt.setString(2, "бакалавр");

            ResultSet rs = pstmt.executeQuery();

            System.out.println("Данные:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") +
                        ", Имя: " + rs.getString("name") +
                        ", Курс: " + rs.getInt("course") +
                        ", Уровень: " + rs.getString("level"));
            }

        } catch (SQLException e) {
            System.out.println("Ошибка при чтении данных: " + e.getMessage());
        }
    }
}