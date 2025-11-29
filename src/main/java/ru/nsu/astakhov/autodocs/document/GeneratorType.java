package ru.nsu.astakhov.autodocs.document;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.nsu.astakhov.autodocs.mapper.AcademicPeriodMapper;
import ru.nsu.astakhov.autodocs.model.*;

@Getter
@RequiredArgsConstructor
// TODO: переименовать в тип документа или тип шаблона?
public enum GeneratorType {

    /// ________BACHELOR'S PROGRAMS________

    APPLICATION_INTERNSHIP_BACH_5SEM_SECS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.FIFTH_SEMESTER, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    APPLICATION_INTERNSHIP_BACH_6SEM_SECS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.SIXTH_SEMESTER, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INDIVIDUAL_ASSIGNMENT_BACH_3COURSE_SECS(
            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, AcademicPeriod.THIRD_COURSE, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_REPORT_BACH_3COURSE_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, AcademicPeriod.THIRD_COURSE, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    INTERNSHIP_SUPERVISOR_REVIEW_BACH_3COURSE_SECS(
            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, AcademicPeriod.THIRD_COURSE, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),



    APPLICATION_INTERNSHIP_BACH_7SEM_SECS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.SEVENTH_SEMESTER, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
    APPLICATION_INTERNSHIP_BACH_8SEM_SECS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.EIGHTH_SEMESTER, Specialization.SOFTWARE_ENGINEERING_AND_CS
    ),
//    INDIVIDUAL_ASSIGNMENT_BACH4_SECS(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
//    ),
//    INTERNSHIP_REPORT_BACH4_SECS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_BACH4_SECS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.SOFTWARE_ENGINEERING_AND_CS
//    ),
//
//

    // TODO: одинаковые шаблоны при отображении в GUI
    APPLICATION_INTERNSHIP_BACH_8SEM_CSSE_TECH(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.EIGHTH_SEMESTER, Specialization.CS_AND_SYSTEMS_ENGINEERING
    ),
    APPLICATION_INTERNSHIP_BACH_8SEM_CSSE_RES(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.BACHELORS, AcademicPeriod.EIGHTH_SEMESTER, Specialization.CS_AND_SYSTEMS_ENGINEERING
    ),
//    INDIVIDUAL_ASSIGNMENT_BACH4_CSSE(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
//    ),
//    INTERNSHIP_REPORT_BACH4_CSSE(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_BACH4_CSSE(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.BACHELORS, AcademicPeriod.FOURTH, Specialization.CS_AND_SYSTEMS_ENGINEERING
//    ),
//

    /// ________MASTER'S PROGRAMS________

    APPLICATION_INTERNSHIP_MAST_1SEM_AIDS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.FIRST_SEMESTER, Specialization.AI_AND_DATA_SCIENCE
    ),
    APPLICATION_INTERNSHIP_MAST_2SEM_AIDS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.SECOND_SEMESTER, Specialization.AI_AND_DATA_SCIENCE
    ),
//    INDIVIDUAL_ASSIGNMENT_MAST1_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.AI_AND_DATA_SCIENCE
//    ),
//    INTERNSHIP_REPORT_MAST1_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.AI_AND_DATA_SCIENCE
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.AI_AND_DATA_SCIENCE
//    ),
//

    APPLICATION_INTERNSHIP_MAST_1SEM_SSD(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.FIRST_SEMESTER, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
    APPLICATION_INTERNSHIP_MAST_2SEM_SSD(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.SECOND_SEMESTER, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    ),
//    INDIVIDUAL_ASSIGNMENT_MAST1_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//    INTERNSHIP_REPORT_MAST1_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//

    APPLICATION_INTERNSHIP_MAST_1SEM_IT(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.FIRST_SEMESTER, Specialization.INTERNET_OF_THINGS
    ),
    APPLICATION_INTERNSHIP_MAST_2SEM_IT(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.SECOND_SEMESTER, Specialization.INTERNET_OF_THINGS
    ),
//    INDIVIDUAL_ASSIGNMENT_MAST1_IT(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.INTERNET_OF_THINGS
//    ),
//    INTERNSHIP_REPORT_MAST1_IT(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.INTERNET_OF_THINGS
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST1_IT(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.FIRST, Specialization.INTERNET_OF_THINGS
//    ),
//


    APPLICATION_INTERNSHIP_MAST_4SEM_AIDS(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.FOURTH_SEMESTER, Specialization.AI_AND_DATA_SCIENCE
    ),
//    INDIVIDUAL_ASSIGNMENT_MAST2_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.AI_AND_DATA_SCIENCE
//    ),
//    INTERNSHIP_REPORT_MAST2_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.AI_AND_DATA_SCIENCE
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_AIDS(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.AI_AND_DATA_SCIENCE
//    ),
//


    APPLICATION_INTERNSHIP_MAST_4SEM_SSD(
            WorkType.INTERNSHIP, DocumentKind.APPLICATION_INTERNSHIP, Degree.MASTERS, AcademicPeriod.FOURTH_SEMESTER, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
    );
//    INDIVIDUAL_ASSIGNMENT_MAST2_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//    INTERNSHIP_REPORT_MAST2_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_SSD(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.SOFTWARE_SYSTEMS_DEVELOPMENT
//    ),
//

//    INDIVIDUAL_ASSIGNMENT_MAST2_IT(
//            WorkType.INTERNSHIP, DocumentKind.INDIVIDUAL_ASSIGNMENT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.INTERNET_OF_THINGS
//    ),
//    INTERNSHIP_REPORT_MAST2_IT(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_REPORT, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.INTERNET_OF_THINGS
//    ),
//    INTERNSHIP_SUPERVISOR_REVIEW_MAST2_IT(
//            WorkType.INTERNSHIP, DocumentKind.INTERNSHIP_SUPERVISOR_REVIEW, Degree.MASTERS, AcademicPeriod.SECOND, Specialization.INTERNET_OF_THINGS
//    );


    private final WorkType workType;
    private final DocumentKind documentKind;
    private final Degree degree;
    private final AcademicPeriod academicPeriod;
    private final Specialization specialization;

    public boolean isSuitable(
            WorkType workType, Degree degree,AcademicPeriod academicPeriod, Specialization specialization
    ) {
        return (workType == null || workType == this.workType)
                && (degree == null || degree == this.degree)
                && isSuitableAcademicPeriod(academicPeriod)
                && (specialization == null || specialization == this.specialization);
    }

    public Course getCourse() {
        return AcademicPeriodMapper.getCourseFromAcademicPeriod(academicPeriod);
    }

    @NotNull
    public String getDisplayName() {
        return workType.getValue() + ": " + documentKind.getValue() + "\n"
                + degree.getValue() + ", " + academicPeriod.getValue() + "\n"
                + specialization.getValue();
    }

    private boolean isSuitableAcademicPeriod(AcademicPeriod academicPeriod) {
        if (academicPeriod == null) {
            return true;
        }

        if (academicPeriod == AcademicPeriod.FIRST_COURSE || academicPeriod == AcademicPeriod.SECOND_COURSE
        || academicPeriod == AcademicPeriod.THIRD_COURSE || academicPeriod == AcademicPeriod.FOURTH_COURSE) {
            return this.getCourse() == AcademicPeriodMapper.getCourseFromAcademicPeriod(academicPeriod);
        }

        return academicPeriod == this.academicPeriod;
    }
}
