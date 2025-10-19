package ru.nsu.astakhov.autodocs.core.services;

import ru.nsu.astakhov.autodocs.core.repositories.*;

public record Student (
        Long id,
        String fullName,
        Course course,
        String email,
        String phoneNumber,
        EduProgram eduProgram,
        String groupName,
        Specialization specialization,
        String orderOnApprovalTopic,
        String orderOnCorrectionTopic,
        String actualSupervisor,
        String thesisCoSupervisor,
        String thesisConsultant,
        String thesisTopic,
        String reviewer,
        InternshipType internshipType,
        Supervisor thesisSupervisor,
        String fullOrganizationName,
        Supervisor thesisNSUSupervisor,
        Supervisor thesisOrganisationSupervisor,
        String fullPlaceOfInternship,
        String organizationName
) {
}
