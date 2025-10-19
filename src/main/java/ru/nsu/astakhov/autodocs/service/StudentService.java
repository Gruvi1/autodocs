package ru.nsu.astakhov.autodocs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.repository.StudentRepository;
import ru.nsu.astakhov.autodocs.mapper.StudentMapper;

@Slf4j
@Service
public class StudentService {
    private final StudentRepository repository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository repository, StudentMapper studentMapper) {
        this.repository = repository;
        this.studentMapper = studentMapper;
        logger.info("StudentService initialized");
    }

    public StudentDto create(StudentDto dto) {
        logger.debug("Creating new student: {}", dto);

        // id
        if (dto.id() != null) {
            throw new IllegalArgumentException("Student id must be null when creating a new student");
        }

        StudentEntity entity = new StudentEntity(
                null,
                dto.fullName(),
                dto.course(),
                dto.email(),
                dto.phoneNumber(),
                dto.eduProgram(),
                dto.groupName(),
                dto.specialization(),
                dto.orderOnApprovalTopic(),
                dto.orderOnCorrectionTopic(),
                dto.actualSupervisor(),
                dto.thesisCoSupervisor(),
                dto.thesisConsultant(),
                dto.thesisTopic(),
                dto.reviewer(),
                dto.internshipType(),
                dto.thesisSupervisor(),
                dto.fullOrganizationName(),
                dto.thesisNSUSupervisor(),
                dto.thesisOrganisationSupervisor(),
                dto.fullPlaceOfInternship(),
                dto.organizationName()
        );

        StudentEntity savedEntity = repository.save(entity);
        logger.info("Student created successfully with id: {}", savedEntity.getId());

        return studentMapper.toDto(savedEntity);
    }
}
