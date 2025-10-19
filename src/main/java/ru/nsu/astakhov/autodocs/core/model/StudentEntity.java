package ru.nsu.astakhov.autodocs.core.model;

import jakarta.persistence.*;
import lombok.*;
import ru.nsu.astakhov.autodocs.utils.converters.CourseConverter;
import ru.nsu.astakhov.autodocs.utils.converters.EduProgramConverter;
import ru.nsu.astakhov.autodocs.utils.converters.InternshipTypeConverter;
import ru.nsu.astakhov.autodocs.utils.converters.SpecializationConverter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName; // имя

    @Convert(converter = CourseConverter.class)
    @Column(nullable = false)
    private Course course; // курс

    @Column(nullable = false)
    private String email; // почта

    private String phoneNumber; // телефон

    @Convert(converter = EduProgramConverter.class)
    @Column(nullable = false)
    private EduProgram eduProgram; // образовательная программа

    @Column(nullable = false)
    private String groupName; // группа

    @Convert(converter = SpecializationConverter.class)
    @Column(nullable = false)
    private Specialization specialization;  // профиль обучения

    private String orderOnApprovalTopic; // распоряжение об утверждении темы

    private String orderOnCorrectionTopic; // Распоряжение о корректировке темы

    private String actualSupervisor; // с кем по факту работает

    private String thesisCoSupervisor; // соруководитель вкр

    private String thesisConsultant; // консультант вкр

    private String thesisTopic; // тема вкр

    private String reviewer; // рецензент

    @Convert(converter = InternshipTypeConverter.class)
    private InternshipType internshipType; // вид практики

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "thesis_supervisor_name")),
            @AttributeOverride(name = "position", column = @Column(name = "thesis_supervisor_position")),
            @AttributeOverride(name = "degree", column = @Column(name = "thesis_supervisor_degree")),
            @AttributeOverride(name = "title", column = @Column(name = "thesis_supervisor_title"))
    })
    private Supervisor thesisSupervisor; // руководитель вкр

    private String fullOrganizationName; // Название организации, структурного подразделения номер кабинета

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "thesis_nsu_supervisor_name")),
            @AttributeOverride(name = "position", column = @Column(name = "thesis_nsu_supervisor_position")),
            @AttributeOverride(name = "degree", column = @Column(name = "thesis_nsu_supervisor_degree")),
            @AttributeOverride(name = "title", column = @Column(name = "thesis_nsu_supervisor_title"))
    })
    private Supervisor thesisNSUSupervisor; // руководитель вкр от нгу

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "thesis_organisation_supervisor_name")),
            @AttributeOverride(name = "position", column = @Column(name = "thesis_organisation_supervisor_position")),
            @AttributeOverride(name = "degree", column = @Column(name = "thesis_organisation_supervisor_degree")),
            @AttributeOverride(name = "title", column = @Column(name = "thesis_organisation_supervisor_title"))
    })
    private Supervisor thesisOrganisationSupervisor; // руководитель вкр от организации

    private String fullPlaceOfInternship; // Место практики полностью

    private String organizationName; // Наименование организации
}
