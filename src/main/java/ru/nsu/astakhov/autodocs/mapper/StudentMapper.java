package ru.nsu.astakhov.autodocs.mapper;

import ru.nsu.astakhov.autodocs.model.StudentEntity;
import ru.nsu.astakhov.autodocs.model.StudentDto;

import java.util.List;

public final class StudentMapper {
    private StudentMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static List<StudentDto> listToDto(List<StudentEntity> entities) {
        return entities.stream()
                .map(StudentMapper::toDto)
                .toList();
    }

    public static StudentDto toDto(StudentEntity entity) {
        return new StudentDto(
                entity.getId(),
                entity.getFullName(),
                entity.getGender(),
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
                entity.getNSUSupervisor(),
                entity.getOrganizationSupervisor(),
                entity.getAdministrativeActFromOrganization(),
                entity.getFullPlaceOfInternship(),
                entity.getOrganizationName()
        );
    }

    public static List<StudentEntity> listToEntity(List<StudentDto> dtos) {
        return dtos.stream()
                .map(StudentMapper::toEntity)
                .toList();
    }

    public static StudentEntity toEntity(StudentDto dto) {
        return new StudentEntity(
                dto.id(),
                dto.fullName(),
                dto.gender(),
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
                dto.NSUSupervisor(),
                dto.organizationSupervisor(),
                dto.administrativeActFromOrganization(),
                dto.fullPlaceOfInternship(),
                dto.organizationName()
        );
    }
}
