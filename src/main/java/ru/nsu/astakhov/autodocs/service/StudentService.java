package ru.nsu.astakhov.autodocs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.repository.StudentRepository;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;
    private final GoogleSheetsService googleSheetsService;

    public void readAllData() {
        List<StudentDto> studentDtosFromInternship = googleSheetsService.readAllInternshipLists();
        createStudentsList(studentDtosFromInternship);

        List<StudentDto> studentDtosFromThesis = googleSheetsService.readAllThesisLists();
        updateEmptyField(studentDtosFromThesis);
    }

    public void createStudentsList(List<StudentDto> studentDtos) {
        logger.debug("Creating new students list: {}", studentDtos);

        // TODO: добавить проверки на корректность данных
        // TODO: сначала в цикле всех проверить, потом добавлять

        List<StudentEntity> entities = studentDtos.stream()
                .map(studentMapper::toEntity)
                .toList();

        List<StudentEntity> savedEntities = repository.saveAll(entities);
        logger.info("Students list created successfully with length: {}", savedEntities.size());
    }

    public void updateEmptyField(List<StudentDto> studentDtos) {
        List<StudentEntity> existingEntities = repository.findAll();
        Map<String, StudentEntity> existingByEmail = existingEntities.stream()
                .collect(Collectors.toMap(StudentEntity::getEmail, Function.identity()));

        List<StudentEntity> entitiesToSave = new ArrayList<>();

        for (StudentDto dto : studentDtos) {
            StudentEntity entity = existingByEmail.get(dto.email());

            if (entity == null) {
                entity = studentMapper.toEntity(dto);
            }
            else {
                mergeNullFields(entity, dto);
            }
            entitiesToSave.add(entity);
        }

        repository.saveAll(entitiesToSave);
        logger.info("Update successful");
    }

    public void createStudent(StudentDto dto) {
        logger.debug("Creating new student: {}", dto);

        // TODO: добавить проверки на корректность данных
        if (dto.id() != null) {
            throw new IllegalArgumentException("Student id must be null when creating a new student");
        }

        StudentEntity entity = studentMapper.toEntity(dto);

        StudentEntity savedEntity = repository.save(entity);
        logger.info("Student created successfully with id: {}", savedEntity.getId());
    }

    private void mergeNullFields(StudentEntity entity, StudentDto dto) {
        // TODO: добавить умную проверку данных
        // TODO: если поля и в entity, и в dto определены - сравнить их
        // TODO: если значения не совпали - сказать пользователю и не генерировать для него ничего
        // TODO: либо спросить его, нужно ли генерировать

        entity.setPhoneNumber(dto.phoneNumber());
        entity.setOrderOnApprovalTopic(dto.orderOnApprovalTopic());
        entity.setOrderOnCorrectionTopic(dto.orderOnCorrectionTopic());
        entity.setThesisCoSupervisor(dto.thesisCoSupervisor());
        entity.setThesisConsultant(dto.thesisConsultant());
        entity.setThesisTopic(dto.thesisTopic());
        entity.setReviewer(dto.reviewer());
    }
}
