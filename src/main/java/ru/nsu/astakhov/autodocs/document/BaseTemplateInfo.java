package ru.nsu.astakhov.autodocs.document;

import lombok.Builder;

@Builder
public record BaseTemplateInfo(
        String fileName,
        String yamlFilePath,
        String baseTemplateDir

) {
    private static final String YAML_ROOT_DIR = "/Заполнители";
    private static final String TEMPLATE_ROOT_DIR = "template";

    public static BaseTemplateInfo createFromPath(String yamlFilePath) {
        if (yamlFilePath == null || !yamlFilePath.startsWith(YAML_ROOT_DIR)) {
            throw new IllegalArgumentException("Placeholder path must start with " + YAML_ROOT_DIR + " :" + yamlFilePath);
        }

        if (yamlFilePath.contains("Практика")) {
            return createInternshipFile(yamlFilePath);
        }
        else if (yamlFilePath.contains("ВКР")) {
            return createThesisFile(yamlFilePath);
        }
        else {
            throw new IllegalArgumentException("Unknown yaml path: " + yamlFilePath);
        }
    }

    private static BaseTemplateInfo createInternshipFile(String templatePath) {
        String[] parts = templatePath.split("/");
        int lastSeparatorIndex = templatePath.lastIndexOf("/");
        String templateDir = templatePath.substring(0, lastSeparatorIndex == -1 ? templatePath.length() : lastSeparatorIndex);
        return BaseTemplateInfo.builder()
                // TODO: тут хуйня с -4 может привести к ошибкам, если расширение не .yml, а .yaml
                .fileName(parts[6].substring(0, parts[6].length() - 4))
                .yamlFilePath(templatePath)
                .baseTemplateDir(templateDir.replace(YAML_ROOT_DIR, TEMPLATE_ROOT_DIR))
                .build();
    }

    private static BaseTemplateInfo createThesisFile(String templatePath) {
        String[] parts = templatePath.split("/");
        int lastSeparatorIndex = templatePath.lastIndexOf("/");
        String templateDir = templatePath.substring(0, lastSeparatorIndex == -1 ? templatePath.length() : lastSeparatorIndex);
        return BaseTemplateInfo.builder()
                // TODO: тут хуйня с -4 может привести к ошибкам, если расширение не .yml, а .yaml
                .fileName(parts[5].substring(0, parts[5].length() - 4))
                .yamlFilePath(templatePath)
                .baseTemplateDir(templateDir.replace(YAML_ROOT_DIR, TEMPLATE_ROOT_DIR))
                .build();
    }
}
