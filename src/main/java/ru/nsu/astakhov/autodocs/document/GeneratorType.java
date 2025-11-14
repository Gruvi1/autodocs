package ru.nsu.astakhov.autodocs.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.nsu.astakhov.autodocs.model.*;

@Getter
@RequiredArgsConstructor
// TODO: переименовать?
public enum GeneratorType {
    // TODO: добавить остальные специальности и курсы
    APPLICATION_INTERNSHIP_BACH3_SECS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, Course.THIRD, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),

    INDIVIDUAL_ASSIGNMENT_BACH3_SECS(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, Course.THIRD, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_REPORT_BACH3_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, Course.THIRD, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_BACH3_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, Course.THIRD, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),


    INDIVIDUAL_ASSIGNMENT_BACH4_SECS(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, Course.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_REPORT_BACH4_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, Course.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_BACH4_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, Course.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),


    INDIVIDUAL_ASSIGNMENT_BACH4_CSSE(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, Course.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
    ),
    INTERNSHIP_REPORT_BACH4_CSSE(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, Course.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_BACH4_CSSE(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, Course.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
    ),


    INDIVIDUAL_ASSIGNMENT_MAST1_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.FIRST, Specialization.AI_AND_DATA_SCIENCE
    ),
    INTERNSHIP_REPORT_MAST1_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.FIRST, Specialization.AI_AND_DATA_SCIENCE
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.FIRST, Specialization.AI_AND_DATA_SCIENCE
    ),


    INDIVIDUAL_ASSIGNMENT_MAST1_SSD(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
    INTERNSHIP_REPORT_MAST1_SSD(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_SSD(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),


    INDIVIDUAL_ASSIGNMENT_MAST1_IT(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.FIRST, Specialization.INTERNET_OF_THINGS
    ),
    INTERNSHIP_REPORT_MAST1_IT(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.FIRST, Specialization.INTERNET_OF_THINGS
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_IT(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.FIRST, Specialization.INTERNET_OF_THINGS
    ),


    INDIVIDUAL_ASSIGNMENT_MAST2_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.SECOND, Specialization.AI_AND_DATA_SCIENCE
    ),
    INTERNSHIP_REPORT_MAST2_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.SECOND, Specialization.AI_AND_DATA_SCIENCE
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_AIDS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.SECOND, Specialization.AI_AND_DATA_SCIENCE
    ),


    INDIVIDUAL_ASSIGNMENT_MAST2_SSD(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
    INTERNSHIP_REPORT_MAST2_SSD(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_SSD(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),


    INDIVIDUAL_ASSIGNMENT_MAST2_IT(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, Course.SECOND, Specialization.INTERNET_OF_THINGS
    ),
    INTERNSHIP_REPORT_MAST2_IT(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, Course.SECOND, Specialization.INTERNET_OF_THINGS
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_IT(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, Course.SECOND, Specialization.INTERNET_OF_THINGS
    );

    public boolean isSuitable(WorkType workType, Degree degree, Course course, Specialization specialization) {
        return (workType == null || workType == this.workType)
                && (degree == null || degree == this.degree)
                && (course == null || course == this.course)
                && (specialization == null || specialization == this.specialization);
    }

    @NotNull
    public String getDisplayName() {
        return workType.getValue() + ": " + documentKind.getValue() + "\n"
                + degree.getValue() + ", " + course.getValue() + " курс\n"
                + specialization.getValue();
    }

    private final WorkType workType;
    private final DocumentKind documentKind;
    private final Degree degree;
    private final Course course;
    private final Specialization specialization;
}
