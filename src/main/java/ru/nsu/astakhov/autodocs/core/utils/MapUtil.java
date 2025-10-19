package ru.nsu.astakhov.autodocs.core.utils;

import ru.nsu.astakhov.autodocs.core.repositories.StudentEntity;
import ru.nsu.astakhov.autodocs.core.services.Student;

public class MapUtil {
    public static Student toStudent(StudentEntity entity) {
        return new Student(
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
}
