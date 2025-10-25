package ru.nsu.astakhov.autodocs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.model.*;
import ru.nsu.astakhov.autodocs.repository.StudentRepository;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;
    private final GoogleSheetsService googleSheetsService;

    public void scanAllData() {
        scanInternshipLists();

        scanThesisLists();
    }

    private void scanInternshipLists() {
        List<StudentDto> studentDtos = googleSheetsService.readAllInternshipLists();
        createStudents(studentDtos);
    }

    private void scanThesisLists() {
        List<StudentDto> studentDtos = googleSheetsService.readAllThesisLists();
        updateStudents(studentDtos);
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

        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.email(), "почта");
        notifyIfObjectFieldMissing(PracticeType.INTERNSHIP, studentName, dto.eduProgram(), "образовательная программа");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.groupName(), "группа");
        notifyIfObjectFieldMissing(PracticeType.INTERNSHIP, studentName, dto.specialization(), "профиль обучения");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.actualSupervisor(), "фактический руководитель");
        notifyIfObjectFieldMissing(PracticeType.INTERNSHIP, studentName, dto.internshipType(), "вид практики");

        checkInternshipSupervisor(dto.thesisSupervisor(), dto.fullName());

        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.fullOrganizationName(), "название организации");

        checkInternshipSupervisor(dto.thesisNSUSupervisor(), dto.fullName());
        checkInternshipSupervisor(dto.thesisOrganisationSupervisor(), dto.fullName());

        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.administrativeActFromOrganisation(), "распорядительный акт");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.fullPlaceOfInternship(), "место практики");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, dto.organizationName(), "наименование организации");
    }

    private void notifyIfStringFieldMissing(PracticeType type, String student, String value, String fieldName) {
        if (isNullOrBlank(value)) {
            logger.info(buildMissingFieldMessage(type), student, fieldName);
        }
    }

    private void notifyIfObjectFieldMissing(PracticeType type, String student, Object value, String fieldName) {
        if (value == null) {
            logger.info(buildMissingFieldMessage(type), student, fieldName);
        }
    }

    private String buildMissingFieldMessage(PracticeType practiceType) {
        String tableType = practiceType == PracticeType.INTERNSHIP ? "практике" : "ВКР";
        return "В таблице по " + tableType + " у студента {} не определено поле: {}";
    }

    private void checkInternshipSupervisor(Supervisor supervisor, String studentName) {
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, supervisor.name(), "имя руководителя");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, supervisor.position(), "должность руководителя");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, supervisor.degree(), "учёная степень руководителя");
        notifyIfStringFieldMissing(PracticeType.INTERNSHIP, studentName, supervisor.title(), "учёное звание руководителя");
    }

    private void updateStudents(List<StudentDto> studentDtos) {
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
        Scanner scan = new Scanner(System.in);

        for (StudentDto dto : validDtos) {
            StudentEntity entity = existingByFullName.get(dto.fullName());

            if (entity == null) {
                entity = studentMapper.toEntity(dto);
            }
            else {
                checkCollision(entity, dto, scan);
                mergeFromThesis(entity, dto);
            }
            entitiesToSave.add(entity);
        }

        List<StudentEntity> savedEntity = repository.saveAll(entitiesToSave);
        logger.info("Students updated successfully with length: {}", savedEntity.size());
    }

    private String buildCollisionFieldMessage() {
        return """
                Студент {} имеет разные поля "{}" на листе практики и на листе ВКР
                Выберите, что сохранить (введите цифру):
                практика(1): {} \t ВКР(2): {}
                """;
    }

    private void checkCollision(StudentEntity entity, StudentDto dto, Scanner scan) {
        String studentName = entity.getFullName();

        checkFieldCollision(entity::getEmail, dto::email, entity::setEmail, studentName, "почта", scan);
        checkFieldCollision(entity::getEduProgram, dto::eduProgram, entity::setEduProgram, studentName, "образовательная программа", scan);
        checkFieldCollision(entity::getGroupName, dto::groupName, entity::setGroupName, studentName, "группа", scan);
        checkFieldCollision(entity::getSpecialization, dto::specialization, entity::setSpecialization, studentName, "профиль обучения", scan);
        checkFieldCollision(entity::getActualSupervisor, dto::actualSupervisor, entity::setActualSupervisor, studentName, "фактический руководитель", scan);
    }

    private <T> void checkFieldCollision(
            Supplier<T> entityGetter,
            Supplier<T> dtoGetter,
            Consumer<T> entitySetter,
            String studentName,
            String fieldName,
            Scanner scan
    ) {
       T entityValue = entityGetter.get();
       T dtoValue = dtoGetter.get();
       if (!Objects.equals(entityValue, dtoValue)) {
           logger.info(buildCollisionFieldMessage(), studentName, fieldName, entityValue, dtoValue);
           if (shouldUpdateField(scan)) {
               entitySetter.accept(dtoValue);
           }
       }
    }

    private boolean shouldUpdateField(Scanner scan) {
        int res = scan.nextInt();
        while (res != 1 && res != 2) {
            logger.info("Пожалуйста, выберите 1 или 2");
            res = scan.nextInt();
        }

        return res == 2;
    }

    private void checkThesisDto(StudentDto dto) {
        final String studentName = dto.fullName();

        notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.email(), "почта");
        notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.phoneNumber(), "телефон");
        notifyIfObjectFieldMissing(PracticeType.THESIS, studentName, dto.eduProgram(), "образовательная программа");
        notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.groupName(), "группа");
        notifyIfObjectFieldMissing(PracticeType.THESIS, studentName, dto.specialization(), "профиль обучения");
        notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.actualSupervisor(), "фактический руководитель");

        if (dto.course() != Course.THIRD) {
            notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.orderOnApprovalTopic(), "распоряжение об утверждении");
            notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.orderOnCorrectionTopic(), "распоряжение о корректировке");
            notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.thesisCoSupervisor(), "соруководитель");
            notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.thesisConsultant(), "консультант");
            notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.thesisTopic(), "тема ВКР");

            if (dto.course() != Course.FOURTH) {
                notifyIfStringFieldMissing(PracticeType.THESIS, studentName, dto.reviewer(), "рецензент");
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
