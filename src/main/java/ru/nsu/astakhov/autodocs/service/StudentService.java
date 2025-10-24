package ru.nsu.astakhov.autodocs.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.model.Supervisor;
import ru.nsu.astakhov.autodocs.repository.StudentRepository;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
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
//        if (dto.id() != null) {
//            throw new IllegalArgumentException("Student id must be null when creating a new student");
//        }
//        if (isNullOrBlank(dto.fullName())) {
//            throw new IllegalArgumentException("Student name must be initialized");
//        }
        if (isNull(dto.course())) {
            logger.info("В таблице по практике у студента {} не определён курс", dto.fullName());
        }
        if (isNullOrBlank(dto.email())) {
            logger.info("В таблице по практике у студента {} не определена почта", dto.fullName());
        }
        if (isNull(dto.eduProgram())) {
            logger.info("В таблице по практике у студента {} не определена образовательная программа", dto.fullName());
        }
        if (isNullOrBlank(dto.groupName())) {
            logger.info("В таблице по практике у студента {} не определена группа", dto.fullName());
        }
        if (isNull(dto.specialization())) {
            logger.info("В таблице по практике у студента {} не определён профиль обучения", dto.fullName());
        }
        if (isNullOrBlank(dto.actualSupervisor())) {
            logger.info("В таблице по практике у студента {} не определён фактический руководитель", dto.fullName());
        }
        if (isNull(dto.internshipType())) {
            logger.info("В таблице по практике у студента {} не определён вид практики", dto.fullName());
        }
        checkInternshipSupervisor(dto.thesisSupervisor(), dto.fullName());
        if (isNullOrBlank(dto.fullOrganizationName())) {
            logger.info("В таблице по практике у студента {} не определено полное название организации", dto.fullName());
        }
        checkInternshipSupervisor(dto.thesisNSUSupervisor(), dto.fullName());
        checkInternshipSupervisor(dto.thesisOrganisationSupervisor(), dto.fullName());
        if (isNullOrBlank(dto.administrativeActFromOrganisation())) {
            logger.info("В таблице по практике у студента {} не определён распорядительный акт", dto.fullName());
        }
        if (isNullOrBlank(dto.fullPlaceOfInternship())) {
            logger.info("В таблице по практике у студента {} не определено место практики", dto.fullName());
        }
        if (isNullOrBlank(dto.organizationName())) {
            logger.info("В таблице по практике у студента {} не определено наименование организации", dto.fullName());
        }
        logger.info("");
    }

    private void checkInternshipSupervisor(Supervisor supervisor, String studentName) {
        if (isNullOrBlank(supervisor.name())) {
            logger.info("В таблице по практике у студента {} не определено имя руководителя", studentName);
        }
        if (isNullOrBlank(supervisor.position())) {
            logger.info("В таблице по практике у студента {} не определена должность руководителя", studentName);
        }
        if (isNullOrBlank(supervisor.degree())) {
            logger.info("В таблице по практике у студента {} не определена учёная степень руководителя", studentName);
        }
        if (isNullOrBlank(supervisor.title())) {
            logger.info("В таблице по практике у студента {} не определено учёное звание руководителя", studentName);
        }
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

        for (StudentDto dto : validDtos) {
            StudentEntity entity = existingByFullName.get(dto.fullName());

            if (entity == null) {
                entity = studentMapper.toEntity(dto);
            }
            else {
                checkCollision(entity, dto);
                mergeFromThesis(entity, dto);
            }
            entitiesToSave.add(entity);
        }

        List<StudentEntity> savedEntity = repository.saveAll(entitiesToSave);
        logger.info("Students updated successfully with length: {}", savedEntity.size());
    }

    private void checkCollision(StudentEntity entity, StudentDto dto) {
        Scanner scan = new Scanner(System.in);
        if (!entity.getEmail().equals(dto.email())) {
            System.out.println("Студент " + entity.getFullName() +
                    "имеет разные почты на листе практике и на листе ВКР");
            System.out.println("Выберите, какую почту сохранить (введите цифру):\n" +
                    "1)" + entity.getEmail() + " 2)" + dto.email());
            int res = scan.nextInt(); // TODO: может ввести не 1, 2
            // TODO: если 1, то ничего не делаем, потому что нужное уже в entity
            if (res == 2) {
                entity.setEmail(dto.email());
            }
        }
        if (entity.getEduProgram() != dto.eduProgram()) {
            System.out.println("Студент " + entity.getFullName() +
                    "имеет разные образовательные программы на листе практике и на листе ВКР");
            System.out.println("Выберите, какую программу сохранить (введите цифру):\n" +
                    "1)" + entity.getEduProgram() + " 2)" + dto.eduProgram());
            int res = scan.nextInt(); // TODO: может ввести не 1, 2
            // TODO: если 1, то ничего не делаем, потому что нужное уже в entity
            if (res == 2) {
                entity.setEduProgram(dto.eduProgram());
            }
        }
        if (!entity.getGroupName().equals(dto.groupName())) {
            System.out.println("Студент " + entity.getFullName() +
                    "имеет разные группы на листе практике и на листе ВКР");
            System.out.println("Выберите, какую группу сохранить (введите цифру):\n" +
                    "1)" + entity.getGroupName() + " 2)" + dto.groupName());
            int res = scan.nextInt(); // TODO: может ввести не 1, 2
            // TODO: если 1, то ничего не делаем, потому что нужное уже в entity
            if (res == 2) {
                entity.setGroupName(dto.groupName());
            }
        }
        if (entity.getSpecialization() != dto.specialization()) {
            System.out.println("Студент " + entity.getFullName() +
                    "имеет разные профили обучения на листе практике и на листе ВКР");
            System.out.println("Выберите, какой профиль сохранить (введите цифру):\n" +
                    "1)" + entity.getSpecialization() + " 2)" + dto.specialization());
            int res = scan.nextInt(); // TODO: может ввести не 1, 2
            // TODO: если 1, то ничего не делаем, потому что нужное уже в entity
            if (res == 2) {
                entity.setSpecialization(dto.specialization());
            }
        }
        if (!entity.getActualSupervisor().equals(dto.actualSupervisor())) {
            System.out.println("Студент " + entity.getFullName() +
                    "имеет разных фактических руководителей обучения на листе практике и на листе ВКР");
            System.out.println("Выберите, какого руководителя сохранить (введите цифру):\n" +
                    "1)" + entity.getActualSupervisor() + " 2)" + dto.actualSupervisor());
            int res = scan.nextInt(); // TODO: может ввести не 1, 2
            // TODO: если 1, то ничего не делаем, потому что нужное уже в entity
            if (res == 2) {
                entity.setActualSupervisor(dto.actualSupervisor());
            }
        }
    }


    private void checkThesisDto(StudentDto dto) {
        if (isNull(dto.course())) {
            logger.info("В таблице по ВКР у студента {} не определён курс", dto.fullName());
        }
        if (isNullOrBlank(dto.email())) {
            logger.info("В таблице по ВКР у студента {} не определена почта", dto.fullName());
        }
        if (isNullOrBlank(dto.phoneNumber())) {
            logger.info("В таблице по ВКР у студента {} не определен телефон", dto.fullName());
        }
        if (isNull(dto.eduProgram())) {
            logger.info("В таблице по ВКР у студента {} не определена образовательная программа", dto.fullName());
        }
        if (isNullOrBlank(dto.groupName())) {
            logger.info("В таблице по ВКР у студента {} не определена группа", dto.fullName());
        }
        if (isNull(dto.specialization())) {
            logger.info("В таблице по ВКР у студента {} не определён профиль обучения", dto.fullName());
        }
        if (isNullOrBlank(dto.actualSupervisor())) {
            logger.info("В таблице по ВКР у студента {} не определён фактический руководитель", dto.fullName());
        }
        if (dto.course() != Course.THIRD) {
            if (isNullOrBlank(dto.orderOnApprovalTopic())) {
                logger.info("В таблице по ВКР у студента {} не определено распоряжение об утверждении", dto.fullName());
            }
            if (isNullOrBlank(dto.orderOnCorrectionTopic())) {
                logger.info("В таблице по ВКР у студента {} не определено распоряжение о корректировке", dto.fullName());
            }
            if (isNullOrBlank(dto.thesisCoSupervisor())) {
                logger.info("В таблице по ВКР у студента {} не определён соруководитель", dto.fullName());
            }
            if (isNullOrBlank(dto.thesisConsultant())) {
                logger.info("В таблице по ВКР у студента {} не определён консультант", dto.fullName());
            }
            if (isNullOrBlank(dto.thesisTopic())) {
                logger.info("В таблице по ВКР у студента {} не определена тема ВКР", dto.fullName());
            }
            if (dto.course() != Course.FOURTH && isNullOrBlank(dto.reviewer())) {
                logger.info("В таблице по ВКР у студента {} не определён рецензент", dto.fullName());
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

    // TODO: убрать метод? Вызов функции же замедляет программу
    // TODO: особенно вызов в цикле
    private boolean isNull(Object field) {
        return field == null;
    }
}
