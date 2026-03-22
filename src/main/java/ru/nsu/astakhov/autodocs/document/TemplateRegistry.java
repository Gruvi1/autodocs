package ru.nsu.astakhov.autodocs.document;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class TemplateRegistry {
    private static final String BASE_PATH = "template";
    private final List<PreparedTemplateInfo> templates;

    public TemplateRegistry() {
        templates = new ArrayList<>();
    }

    @PostConstruct
    private void scan() {
        try {
            create();
            save();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void create() {
        new TemplateCreator().start();
    }

    private void save() throws IOException {
        Path rootPath = Paths.get("template");

        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".docx"))
                    .forEach(path -> {
                        String relativePath = BASE_PATH + "/" +
                                rootPath.relativize(path).toString().replace('\\', '/');

                        PreparedTemplateInfo preparedTemplateInfo;
                        try {
                            preparedTemplateInfo = PreparedTemplateInfo.createFromPath(relativePath);
                            templates.add(preparedTemplateInfo);
                        }
                        catch (Exception e) {
                            logger.error(e.getMessage());
                        }
                    });
        }
    }
}
