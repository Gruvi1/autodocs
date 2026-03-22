package ru.nsu.astakhov.autodocs.document;

import org.springframework.core.io.ClassPathResource;
import ru.nsu.astakhov.autodocs.document.generator.PreparedTemplateGenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TemplateCreator {
    private static final String BASE_PATH = "/Заполнители";

    private final Map<String, String> baseTemplates;
    private final Map<String, String> placeholders;

    public TemplateCreator() {
        baseTemplates = new HashMap<>();
        placeholders = new HashMap<>();

        try {
            scanBaseTemplates();
            scanPlaceholders();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start() {
        for (Map.Entry<String, String> entry : baseTemplates.entrySet()) {
            analyzeBaseTemplate(entry.getKey(), entry.getValue());
        }
    }

    private void scanBaseTemplates() throws IOException {
        ClassPathResource resource = new ClassPathResource(BASE_PATH);
        Path rootPath = Paths.get(resource.getURI());
        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".docx"))
                    .forEach(path -> {
                        String relativePath = BASE_PATH + "/" +
                                rootPath.relativize(path).toString().replace('\\', '/');
                        baseTemplates.put(relativePath, path.getFileName().toString().replace(".docx", ""));
                    });
        }
    }

    private void scanPlaceholders() throws IOException {
        ClassPathResource resource = new ClassPathResource(BASE_PATH);
        Path rootPath = Paths.get(resource.getURI());

        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".yml") || path.toString().endsWith(".yaml"))
                    .forEach(path -> {
                        String relativePath = BASE_PATH + "/" +
                                rootPath.relativize(path).toString().replace('\\', '/');
                        placeholders.put(relativePath, path.getFileName().toString().replace(".docx", ""));
                    });
        }
    }

    private void analyzeBaseTemplate(String templateFilePath, String templateName) {
        PreparedTemplateGenerator preparedTemplateGenerator = new PreparedTemplateGenerator(templateFilePath);
        for (String yamlFilePath : placeholders.keySet()) {
            if (placeholders.get(yamlFilePath).startsWith(templateName)) {
                preparedTemplateGenerator.generate(BaseTemplateInfo.createFromPath(yamlFilePath));
            }
        }
    }
}
