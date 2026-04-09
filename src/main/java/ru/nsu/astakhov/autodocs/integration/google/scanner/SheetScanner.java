package ru.nsu.astakhov.autodocs.integration.google.scanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

@Slf4j
@Service
public class SheetScanner {
    @Qualifier("sheetsTaskExecutor")
    private final ThreadPoolTaskExecutor sheetsTaskExecutor;
    
    private final GoogleSheetsService googleSheetsService;

    public SheetScanner(ThreadPoolTaskExecutor sheetsTaskExecutor, GoogleSheetsService googleSheetsService) {
        this.sheetsTaskExecutor = sheetsTaskExecutor;
        this.googleSheetsService = googleSheetsService;
    }

    public List<StudentDto> readAllInternshipLists() {
        return submitReadTask(course -> readSheetList(new InternshipStrategy(), course));
    }

    public List<StudentDto> readAllThesisLists() {
        return submitReadTask(course -> readSheetList(new ThesisStrategy(), course));
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

    private List<StudentDto> readSheetList(SheetStrategy strategy, Course course) {
        String range = strategy.buildRange(course);
        List<List<Object>> rows;
        try {
            rows = googleSheetsService.readRangeFromSheet(range);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        int startIndex = 1;
        int finalIndex = SheetUtil.findLastRowIndex(rows);
        var subRows = rows.subList(startIndex, finalIndex);

        return subRows.stream()
                .map(row -> {
                    if (strategy.hasSpecialParsing(course)) {
                        return strategy.parseSpecialRow(row, course);
                    }
                    return strategy.parseRow(row, course);
                })
                .filter(Objects::nonNull)
                .toList();
    }
}
