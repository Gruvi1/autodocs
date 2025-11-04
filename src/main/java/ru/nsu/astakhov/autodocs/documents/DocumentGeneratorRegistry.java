package ru.nsu.astakhov.autodocs.documents;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentGeneratorRegistry {
    private final ApplicationContext applicationContext;

    public DocumentGenerator getDocumentGenerator(GeneratorType type) {
        return switch (type) {
            case IND_WORK_BACH3 -> applicationContext.getBean(IndWorkBach3Generator.class);
        };
    }
}
