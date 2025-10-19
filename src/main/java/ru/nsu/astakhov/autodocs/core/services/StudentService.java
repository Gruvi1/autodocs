package ru.nsu.astakhov.autodocs.core.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.core.model.StudentEntity;
import ru.nsu.astakhov.autodocs.core.repositories.StudentRepository;
import ru.nsu.astakhov.autodocs.utils.MapUtil;

@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
        logger.info("StudentService initialized");
    }


    public Student create(Student dto) {
        logger.debug("Creating new transaction: {}", dto);

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

        return MapUtil.toStudent(savedEntity);
    }

}
