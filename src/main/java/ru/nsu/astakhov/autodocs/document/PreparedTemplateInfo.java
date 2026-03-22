package ru.nsu.astakhov.autodocs.document;

import lombok.Builder;
import org.jetbrains.annotations.NotNull;
import ru.nsu.astakhov.autodocs.mapper.AcademicPeriodMapper;
import ru.nsu.astakhov.autodocs.model.AcademicPeriod;
import ru.nsu.astakhov.autodocs.model.Course;
import ru.nsu.astakhov.autodocs.model.Degree;
import ru.nsu.astakhov.autodocs.model.Specialization;
import ru.nsu.astakhov.autodocs.model.WorkType;

@Builder
public record PreparedTemplateInfo(
        WorkType workType,
        Degree degree,
        Specialization specialization,
        AcademicPeriod academicPeriod,
        String fileName,
        String templatePath,
        String documentDir
) {
    // TODO: добавить защиту от неверных файлов !!!!
    public static PreparedTemplateInfo createFromPath(String templatePath) {
        if (templatePath == null || !templatePath.startsWith("template")) {
            throw new IllegalArgumentException("Template path must start with template");
        }

        if (templatePath.contains("Практика")) {
            return createInternshipFile(templatePath);
        }
        else if (templatePath.contains("ВКР")) {
            return createThesisFile(templatePath);
        }
        else {
            throw new IllegalArgumentException("Unknown placeholder path: " + templatePath);
        }
    }

    public boolean isSuitable(
            WorkType workType, Degree degree, AcademicPeriod academicPeriod, Specialization specialization
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
        return workType.getValue() + ": " + fileName.replace('_', ' ') + "\n"
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

    private static PreparedTemplateInfo createInternshipFile(String templatePath) {
        String[] parts = templatePath.split("/");
        int lastSeparatorIndex = templatePath.lastIndexOf("/");
        String templateDir = templatePath.substring(0, lastSeparatorIndex == -1 ? templatePath.length() : lastSeparatorIndex);
        return PreparedTemplateInfo.builder()
                .workType(WorkType.fromValue(parts[1]))
                .degree(Degree.fromValue(parts[2]))
                .specialization(Specialization.fromValue(parts[3]))
                .academicPeriod(AcademicPeriod.fromValue(parts[4]))
                .fileName(parts[5].substring(0, parts[5].length() - 5))
                .templatePath(templatePath)
                .documentDir(templateDir.replace("template", "document"))
                .build();
    }

    private static PreparedTemplateInfo createThesisFile(String templatePath) {
        String[] parts = templatePath.split("/");
        int lastSeparatorIndex = templatePath.lastIndexOf("/");
        String templateDir = templatePath.substring(0, lastSeparatorIndex == -1 ? templatePath.length() : lastSeparatorIndex);
        return PreparedTemplateInfo.builder()
                .workType(WorkType.fromValue(parts[1]))
                .degree(Degree.fromValue(parts[2]))
                .specialization(Specialization.fromValue(parts[3]))
                .academicPeriod(Degree.fromValue(parts[2]) == Degree.BACHELORS ? AcademicPeriod.FOURTH_COURSE : AcademicPeriod.SECOND_COURSE)
                .fileName(parts[4].substring(0, parts[4].length() - 5))
                .templatePath(templatePath)
                .documentDir(templateDir.replace("template", "document"))
                .build();
    }
}
