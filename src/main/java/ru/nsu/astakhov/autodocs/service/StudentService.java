package ru.nsu.astakhov.autodocs.service;

import  jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.generator.IndAssignmentBach3Generator;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.model.*;
import ru.nsu.astakhov.autodocs.repository.StudentRepository;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;
    private final GoogleSheetsService googleSheetsService;
    private final IndAssignmentBach3Generator indAssignmentBach3Generator;
    private final WarningList warningList;

    public void scanAllData() {
        clearAllData();
        scanInternshipLists();
        scanThesisLists();
    }

    @Transactional
    public void clearAllData() {
        System.out.println("EEEEE");
        warningList.clear();
        repository.deleteAll();
    }

    public void createIndWorkDoc() {
        // TODO: убрать явное имя
        StudentEntity entity = repository.findByFullName("Зималтынов Кирилл Русланович")
                .orElseThrow(() ->new EntityNotFoundException("Нет такого =("));
        StudentDto dto = studentMapper.toDto(entity);

        indAssignmentBach3Generator.generate(dto);
    }

    public void scanInternshipLists() {
        List<StudentDto> studentDtos = googleSheetsService.readAllInternshipLists();
        createStudents(studentDtos);
    }

    public List<FieldCollision> scanThesisLists() {
        List<StudentDto> studentDtos = googleSheetsService.readAllThesisLists();
        return updateStudents(studentDtos);
    }

    public void saveResolvedField(List<FieldCollision> resolvedCollisions) {
        List<StudentEntity> entities = resolvedCollisions.stream().map(FieldCollision::entity).toList();

        repository.saveAll(entities);
    }

    private void createStudents(List<StudentDto> studentDtos) {
        logger.info("Creating new students with length: {}", studentDtos.size());

        List<StudentDto> validDtos = new ArrayList<>();
        for (StudentDto dto : studentDtos) {
            if (isNullOrBlank(dto.fullName())) {
                logger.error("Error: Student name must be initialized on creation");
                continue;
            }
            if (dto.id() != null) {
                logger.error("Error: Student id must be null when creating a new student");
                checkInternshipDto(dto);
                continue;
            }
            checkInternshipDto(dto);
            validDtos.add(dto);
        }

        List<StudentEntity> entities = validDtos.stream()
                .map(studentMapper::toEntity)
                .toList();

        List<StudentEntity> savedEntities = repository.saveAll(entities);
        logger.info("Students created successfully with length: {}", savedEntities.size());
    }

    private void checkInternshipDto(StudentDto dto) {
        final String studentName = dto.fullName();

        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.email(), "почта");
        notifyIfObjectFieldMissing(WorkType.INTERNSHIP, studentName, dto.eduProgram(), "образовательная программа");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.groupName(), "группа");
        notifyIfObjectFieldMissing(WorkType.INTERNSHIP, studentName, dto.specialization(), "профиль обучения");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.actualSupervisor(), "фактический руководитель");
        notifyIfObjectFieldMissing(WorkType.INTERNSHIP, studentName, dto.internshipType(), "вид практики");

        checkInternshipSupervisor(dto.thesisSupervisor(), dto.fullName());

        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.fullOrganizationName(), "название организации");

        checkInternshipSupervisor(dto.NSUSupervisor(), dto.fullName());
        checkInternshipSupervisor(dto.organizationSupervisor(), dto.fullName());

        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.administrativeActFromOrganization(), "распорядительный акт");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.fullPlaceOfInternship(), "место практики");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, dto.organizationName(), "наименование организации");
    }

    private void notifyIfStringFieldMissing(WorkType type, String student, String value, String fieldName) {
        if (isNullOrBlank(value)) {
            warningList.addWarning(type, student, fieldName);
        }
    }

    private void notifyIfObjectFieldMissing(WorkType type, String student, Object value, String fieldName) {
        if (value == null) {
            warningList.addWarning(type, student, fieldName);
        }
    }

    private void checkInternshipSupervisor(Supervisor supervisor, String studentName) {
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, supervisor.name(), "имя руководителя");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, supervisor.position(), "должность руководителя");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, supervisor.degree(), "учёная степень руководителя");
        notifyIfStringFieldMissing(WorkType.INTERNSHIP, studentName, supervisor.title(), "учёное звание руководителя");
    }

    private List<FieldCollision> updateStudents(List<StudentDto> studentDtos) {
        logger.info("Updating students with length: {}", studentDtos.size());

        List<StudentDto> validDtos = new ArrayList<>();
        for (StudentDto dto : studentDtos) {
            if (isNullOrBlank(dto.fullName())) {
                logger.error("Error: Student name must be initialized when updating");
                continue;
            }
            if (dto.id() != null) {
                logger.error("Error: Student id must be null when updating student");
                checkThesisDto(dto);
                continue;
            }
            checkThesisDto(dto);
            validDtos.add(dto);
        }

        List<StudentEntity> existingEntities = repository.findAll();
        Map<String, StudentEntity> existingByFullName = existingEntities.stream()
                .collect(Collectors.toMap(StudentEntity::getFullName, Function.identity()));

        List<StudentEntity> entitiesToSave = new ArrayList<>();

        List<FieldCollision> collisions = new ArrayList<>();

        for (StudentDto dto : validDtos) {
            StudentEntity entity = existingByFullName.get(dto.fullName());

            if (entity == null) {
                entity = studentMapper.toEntity(dto);
            }
            else {
                checkCollision(entity, dto, collisions);
                mergeFromThesis(entity, dto); // TODO: временно мержим всё(плохо) из ВКР, но коллизии переопределим позже
            }
            entitiesToSave.add(entity);
        }

        List<StudentEntity> savedEntity = repository.saveAll(entitiesToSave);
        logger.info("Students updated successfully with length: {}", savedEntity.size());

        return collisions;
    }

    private void checkCollision(StudentEntity entity, StudentDto dto, List<FieldCollision> collisions) {
        checkFieldCollision(entity.getEmail(), dto.email(), entity::setEmail, entity, "почта", collisions);
        checkFieldCollision(entity.getEduProgram().getValue(), dto.eduProgram().getValue(), entity::setEduProgram, entity, "образовательная программа", collisions);
        checkFieldCollision(entity.getGroupName(), dto.groupName(), entity::setGroupName, entity, "группа", collisions);
        checkFieldCollision(entity.getSpecialization().getValue(), dto.specialization().getValue(), entity::setSpecialization, entity, "профиль обучения", collisions);
        checkFieldCollision(entity.getActualSupervisor(), dto.actualSupervisor(), entity::setActualSupervisor, entity, "фактический руководитель", collisions);
    }

    private void checkFieldCollision(
            String entityValue,
            String dtoValue,
            Consumer<String> entitySetter,
            StudentEntity entity,
            String fieldName,
            List<FieldCollision> collisions
    ) {
       if (!Objects.equals(entityValue, dtoValue)) {
           collisions.add(new FieldCollision(
                   entity,
                   entity.getFullName(),
                   fieldName,
                   entitySetter,
                   entityValue,
                   dtoValue
           ));
       }
    }

    private void checkThesisDto(StudentDto dto) {
        final String studentName = dto.fullName();

        notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.email(), "почта");
        notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.phoneNumber(), "телефон");
        notifyIfObjectFieldMissing(WorkType.THESIS, studentName, dto.eduProgram(), "образовательная программа");
        notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.groupName(), "группа");
        notifyIfObjectFieldMissing(WorkType.THESIS, studentName, dto.specialization(), "профиль обучения");
        notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.actualSupervisor(), "фактический руководитель");

        if (dto.course() != Course.THIRD) {
            notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.orderOnApprovalTopic(), "распоряжение об утверждении");
            notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.orderOnCorrectionTopic(), "распоряжение о корректировке");
            notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.thesisCoSupervisor(), "соруководитель");
            notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.thesisConsultant(), "консультант");
            notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.thesisTopic(), "тема ВКР");

            if (dto.course() != Course.FOURTH) {
                notifyIfStringFieldMissing(WorkType.THESIS, studentName, dto.reviewer(), "рецензент");
            }
        }
    }

    private void mergeFromThesis(StudentEntity entity, StudentDto dto) {
        entity.setPhoneNumber(dto.phoneNumber());
        entity.setOrderOnApprovalTopic(dto.orderOnApprovalTopic());
        entity.setOrderOnCorrectionTopic(dto.orderOnCorrectionTopic());
        entity.setThesisCoSupervisor(dto.thesisCoSupervisor());
        entity.setThesisConsultant(dto.thesisConsultant());
        entity.setThesisTopic(dto.thesisTopic());
        entity.setReviewer(dto.reviewer());
    }

    private boolean isNullOrBlank(String field) {
        return field == null || field.isBlank();
    }
}
