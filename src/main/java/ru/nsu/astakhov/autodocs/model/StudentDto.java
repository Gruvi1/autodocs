package ru.nsu.astakhov.autodocs.model;

public record StudentDto(
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
        String administrativeActFromOrganisation,
        String fullPlaceOfInternship,
        String organizationName
) {
}
