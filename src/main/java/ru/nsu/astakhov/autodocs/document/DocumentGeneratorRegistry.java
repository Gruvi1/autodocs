package ru.nsu.astakhov.autodocs.document;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.astakhov.autodocs.document.generator.DocumentGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class DocumentGeneratorRegistry {
    private final Map<GeneratorType, DocumentGenerator> generators;

    public DocumentGeneratorRegistry(List<DocumentGenerator> generators) {
        this.generators = generators.stream()
                .collect(Collectors.toMap(DocumentGenerator::getType, Function.identity()));
    }

    public DocumentGenerator getDocumentGenerator(GeneratorType type) {
        DocumentGenerator generator = generators.get(type);
        if (generator == null) {
            throw new IllegalArgumentException("Генератор для типа " + type + " не найден");
        }
        return generator;
    }

    public List<GeneratorType> getAllDocumentTypes() {
        return List.copyOf(generators.keySet());
    }
}
