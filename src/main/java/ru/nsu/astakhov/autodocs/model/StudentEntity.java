package ru.nsu.astakhov.autodocs.model;

import com.github.petrovich4j.Gender;
import jakarta.persistence.*;
import lombok.*;
import ru.nsu.astakhov.autodocs.model.converters.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "students")
public class StudentEntity {
    // TODO: поменять порядок полей
    // TODO: привязать либо к таблице, либо отдельно отсортировать по логике
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName; // фио

    @Convert(converter = GenderConverter.class)
    private Gender gender; // пол

    @Convert(converter = CourseConverter.class)
    @Column(nullable = false)
    private Course course; // курс

    private String email; // почта

    private String phoneNumber; // телефон

    @Convert(converter = EduProgramConverter.class)
    private EduProgram eduProgram; // образовательная программа

//    @Column(nullable = false)
    private String  groupName; // группа

    @Convert(converter = SpecializationConverter.class)
//    @Column(nullable = false)
    private Specialization specialization;  // профиль обучения

    private String orderOnApprovalTopic; // распоряжение об утверждении темы

    private String orderOnCorrectionTopic; // распоряжение о корректировке темы

    private String actualSupervisor; // с кем по факту работает

    private String thesisCoSupervisor; // соруководитель вкр

    private String thesisConsultant; // консультант вкр

    private String thesisTopic; // тема вкр

    private String reviewer; // рецензент

    @Convert(converter = InternshipTypeConverter.class)
    private InternshipType internshipType; // вид практики

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "thesis_supervisor_name"))
    @AttributeOverride(name = "position", column = @Column(name = "thesis_supervisor_position"))
    @AttributeOverride(name = "degree", column = @Column(name = "thesis_supervisor_degree"))
    @AttributeOverride(name = "title", column = @Column(name = "thesis_supervisor_title"))
    private Supervisor thesisSupervisor; // руководитель вкр

    private String fullOrganizationName; // Название организации, структурного подразделения номер кабинета

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "nsu_supervisor_name"))
    @AttributeOverride(name = "position", column = @Column(name = "nsu_supervisor_position"))
    @AttributeOverride(name = "degree", column = @Column(name = "nsu_supervisor_degree"))
    @AttributeOverride(name = "title", column = @Column(name = "nsu_supervisor_title"))
    private Supervisor NSUSupervisor; // руководитель вкр от нгу

    @Embedded
    @AttributeOverride(name = "name", column = @Column(name = "organization_supervisor_name"))
    @AttributeOverride(name = "position", column = @Column(name = "organization_supervisor_position"))
    @AttributeOverride(name = "degree", column = @Column(name = "organization_supervisor_degree"))
    @AttributeOverride(name = "title", column = @Column(name = "organization_supervisor_title"))
    private Supervisor organizationSupervisor; // руководитель вкр от организации

    private String administrativeActFromOrganization; // распорядительный акт от организации

    private String fullPlaceOfInternship; // Место практики полностью

    private String organizationName; // Наименование организации

    public void setEduProgram(String value) {
        EduProgram newEduProgram = EduProgram.fromValue(value);
        if (newEduProgram != null) {
            this.eduProgram = newEduProgram;
        }
    }

    public void setSpecialization(String value) {
        Specialization newSpecialization = Specialization.fromValue(value);
        if (newSpecialization != null) {
            this.specialization = newSpecialization;
        }
    }

    public void setInternshipType(String value) {
        InternshipType newInternshipType = InternshipType.fromValue(value);
        if (newInternshipType != null) {
            this.internshipType = newInternshipType;
        }
    }

    public void setGender(String value) {
        if (Gender.Male.name().equals(value)) {
            this.gender = Gender.Male;
        }
        else if (Gender.Female.name().equals(value)) {
            this.gender = Gender.Female;
        }
    }
}