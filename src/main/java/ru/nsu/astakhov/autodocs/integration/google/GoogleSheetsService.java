package ru.nsu.astakhov.autodocs.integration.google;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
@Service
public class GoogleSheetsService {
    private final Sheets googleSheets;

    @Qualifier("sheetsTaskExecutor")
    private final ThreadPoolTaskExecutor sheetsTaskExecutor;

    @Value("${google.sheets.spreadsheet.id}")
    private String spreadsheetId;

    public GoogleSheetsService(Sheets googleSheets, ThreadPoolTaskExecutor sheetsTaskExecutor) {
        this.googleSheets = googleSheets;
        this.sheetsTaskExecutor = sheetsTaskExecutor;
    }

    public List<StudentDto> readAllInternshipLists() {
        return submitReadTask(this::readInternshipList);
    }

    public List<StudentDto> readAllThesisLists() {
        return submitReadTask(this::readThesisList);
    }

    private List<StudentDto> readInternshipList(Course course) {
        int studentCourse = course.getValue();
        String studentDegree = studentCourse >= 3 ? "бак." : "маг.";

        String range = "Пр " + studentCourse + " к. " + studentDegree;

        List<StudentDto> studentDtos = readListFromInternship(range, course);
        logger.info("Получено {} записей по практике для {} курса {}", studentDtos.size(), studentCourse, studentDegree);
        return studentDtos;
    }

    private List<StudentDto> readThesisList(Course course) {
        int studentCourse = course.getValue();
        String studentDegree = studentCourse >= 3 ? "б." : "м.";

        String range = studentCourse + " курс " + studentDegree;

        List<StudentDto> studentDtos = readListFromThesis(range, course);
        logger.info("Получено {} записей по ВКР для {} курса {}", studentDtos.size(), studentCourse, studentDegree);
        return studentDtos;
    }

    private List<StudentDto> submitReadTask(Function<Course, List<StudentDto>> reader) {
        Future<List<StudentDto>> f1 = sheetsTaskExecutor.submit(() -> reader.apply(Course.FIRST));
        Future<List<StudentDto>> f2 = sheetsTaskExecutor.submit(() -> reader.apply(Course.SECOND));
        Future<List<StudentDto>> f3 = sheetsTaskExecutor.submit(() -> reader.apply(Course.THIRD));
        Future<List<StudentDto>> f4 = sheetsTaskExecutor.submit(() -> reader.apply(Course.FOURTH));

        try {
            List<StudentDto> studentDtos = new ArrayList<>();

            studentDtos.addAll(f1.get());
            studentDtos.addAll(f2.get());
            studentDtos.addAll(f3.get());
            studentDtos.addAll(f4.get());

            return studentDtos;
        }
        // TODO: исправить исключения
        catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private List<StudentDto> readListFromThesis(String range, Course course) {
        List<List<Object>> rows;
        try {
            rows = sendRequest(range);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        int startIndex = 1;
        int finalIndex = getFinalIndex(rows);

        if (course == Course.THIRD) {
            return rows.subList(startIndex, finalIndex).stream()
                    .map(this::parseRowOnlyThirdCourseThesis)
                    .filter(Objects::nonNull)
                    .toList();

        }

        return rows.subList(startIndex, finalIndex).stream()
                .map((row) -> parseRowThesis(row, course))
                .filter(Objects::nonNull)
                .toList();
    }

    private List<StudentDto> readListFromInternship(String range, Course course) {
        List<List<Object>> rows;
        try {
            rows = sendRequest(range);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        int startIndex = 1;
        int finalIndex = getFinalIndex(rows);

        return rows.subList(startIndex, finalIndex).stream()
                .map((row) -> parseRowFromInternship(row, course))
                .filter(Objects::nonNull)
                .toList();
    }

    private int getFinalIndex(List<List<Object>> rows) {
        int size = rows.size();
        if (size < 2) {
            return 1;
        }
        for (int i = size - 1; i != 1; --i) {
            if (rows.get(i).isEmpty()) {
                return i;
            }
        }
        return rows.size();
    }

    private List<List<Object>> sendRequest(String range) throws IOException {
        ValueRange response = googleSheets.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();

        List<List<Object>> rows = response.getValues();
        if (rows == null || rows.isEmpty()) {
            logger.debug("Empty response from range: {}", range);
            return List.of();
        }
        return rows;
    }

    private StudentDto parseRowThesis(List<Object> row, Course course) {
        if (row == null || row.isEmpty()) {
            return null;
        }

        try {
            return new StudentDto(
                    null,
                    getString(row, 0),
                    course,
                    getString(row, 1),
                    getString(row, 2),
                    EduProgram.fromValue(getString(row, 4)),
                    getString(row, 3),
                    Specialization.fromValue(getString(row, 5)),
                    getString(row, 6),
                    getString(row, 7),
                    getString(row, 12),
                    getString(row, 9),
                    getString(row, 10),
                    getString(row, 11),
                    course.getValue() == 4 ? null : getString(row, 13),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
            );
        }
        catch (IllegalArgumentException e) {
            // TODO: тут теряется ошибка, которую кидают ниже
            logger.info(e.getMessage());
            throw new IllegalArgumentException("Ошибка при парсинге строки: " + row, e);
        }
    }

    private StudentDto parseRowOnlyThirdCourseThesis(List<Object> row) {
        if (row == null || row.isEmpty()) {
            return null;
        }
        try {
            return new StudentDto(
                    null,
                    getString(row, 0),
                    Course.THIRD,
                    getString(row, 1),
                    getString(row, 2),
                    EduProgram.fromValue(getString(row, 4)),
                    getString(row, 3),
                    Specialization.fromValue(getString(row, 5)),
                    null,
                    null,
                    getString(row, 7),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null
                    );
        }
        catch (Exception e) {
            // TODO: тут теряется ошибка, которую кидают ниже
            logger.info(e.getMessage());
            throw new IllegalArgumentException("Ошибка при парсинге строки: " + row, e);
        }
    }

    private StudentDto parseRowFromInternship(List<Object> row, Course course) {
        if (row == null || row.isEmpty()) {
            return null;
        }

        try {
            return new StudentDto(
                    null,
                    getString(row, 0),
                    course,
                    getString(row, 20),
                    null,
                    EduProgram.fromValue(getString(row, 21)),
                    getString(row, 2),
                    Specialization.fromValue(getString(row, 1)),
                    null,
                    null,
                    getString(row, 22),
                    null,
                    null,
                    null,
                    null,
                    InternshipType.fromValue(getString(row, 3)),
                    new Supervisor(
                            getString(row, 4),
                            getString(row, 5),
                            getString(row, 6),
                            getString(row, 7)
                    ),
                    getString(row, 8),
                    new Supervisor(
                            getString(row, 9),
                            getString(row, 10),
                            getString(row, 11),
                            getString(row, 12)
                    ),
                    new Supervisor(
                            getString(row, 13),
                            getString(row, 14),
                            getString(row, 15),
                            getString(row, 16)
                    ),
                    getString(row, 17),
                    getString(row, 18),
                    getString(row, 19)
            );
        }
        catch (Exception e) {
            // TODO: тут теряется ошибка, которую кидают ниже
            logger.info(e.getMessage());
            throw new IllegalArgumentException("Ошибка при парсинге строки: " + row, e);
        }
    }

    private String getString(List<Object> row, int index) {
        if (index >= row.size() || row.get(index) == null) {
            return null;
        }
        return row.get(index).toString().trim();
    }
}