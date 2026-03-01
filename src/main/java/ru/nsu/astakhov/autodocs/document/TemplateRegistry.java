package ru.nsu.astakhov.autodocs.document;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Component
@Getter
public class TemplateRegistry {
    private static final String BASE_PATH = "/template";
    private final List<TemplateInfo> templates;

    public TemplateRegistry() {
        templates = new ArrayList<>();
    }

    // TODO: добавить защиту от неверных файлов !!!!
    @PostConstruct
    private void scan() throws IOException {
        ClassPathResource resource = new ClassPathResource(BASE_PATH);
        Path rootPath = Paths.get(resource.getURI());

        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".docx"))
                    .forEach(path -> {

                        String relativePath = BASE_PATH + "/" +
                                rootPath.relativize(path).toString().replace('\\', '/');

                        templates.add(TemplateInfo.createFromPath(relativePath));
                    });
        }
    }
}
