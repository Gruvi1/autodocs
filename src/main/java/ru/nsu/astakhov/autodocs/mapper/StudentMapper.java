package ru.nsu.astakhov.autodocs.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.model.StudentDto;

@Component
public class StudentMapper {
    public StudentDto toDto(StudentEntity entity) {
        return new StudentDto(
                entity.getId(),
                entity.getFullName(),
                entity.getCourse(),
                entity.getEmail(),
                entity.getPhoneNumber(),
                entity.getEduProgram(),
                entity.getGroupName(),
                entity.getSpecialization(),
                entity.getOrderOnApprovalTopic(),
                entity.getOrderOnCorrectionTopic(),
                entity.getActualSupervisor(),
                entity.getThesisCoSupervisor(),
                entity.getThesisConsultant(),
                entity.getThesisTopic(),
                entity.getReviewer(),
                entity.getInternshipType(),
                entity.getThesisSupervisor(),
                entity.getFullOrganizationName(),
                entity.getThesisNSUSupervisor(),
                entity.getThesisOrganisationSupervisor(),
                entity.getFullPlaceOfInternship(),
                entity.getOrganizationName()
        );
    }

    public StudentEntity toEntity(StudentDto dto) {
        return new StudentEntity(
                dto.id(),
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
    }
}
