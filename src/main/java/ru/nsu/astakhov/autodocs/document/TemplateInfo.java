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
public record TemplateInfo(
        WorkType workType,
        Degree degree,
        AcademicPeriod academicPeriod,
        Specialization specialization,
        String fileName,
        String templateDir,
        String documentDir

) {
    // TODO: добавить защиту от неверных файлов !!!!
    public static TemplateInfo createFromPath(String templatePath) {
        if (templatePath == null || !templatePath.startsWith("/template")) {
            throw new IllegalArgumentException("Template path must start with /template");
        }
//        String[] parts = templatePath.replaceAll("^/", "").split("/");
        String[] parts = templatePath.split("/");
        return TemplateInfo.builder()
                .workType(WorkType.fromValue(parts[2]))
                .degree(Degree.fromValue(parts[3]))
                .academicPeriod(AcademicPeriod.fromValue(parts[4]))
                .specialization(Specialization.fromValue(parts[5]))
//                .fileName(parts[6].substring(0, parts[6].length() - 5))
                .fileName(parts[6])
                .templateDir(templatePath)
                .documentDir(templatePath.replace("/template", "document"))
                .build();

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
        return workType.getValue() + ": " + fileName + "\n"
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
