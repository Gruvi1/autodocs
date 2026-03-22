package ru.nsu.astakhov.autodocs.document.generator;

import lombok.RequiredArgsConstructor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import ru.nsu.astakhov.autodocs.document.BaseTemplateInfo;
import ru.nsu.astakhov.autodocs.document.YamlConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;

@RequiredArgsConstructor
public class PreparedTemplateGenerator extends AbstractGenerator<YamlConfig> {
    private final String commonTemplatePath;

    public void generate(BaseTemplateInfo baseTemplateInfo) {
        initOutputDirectory(baseTemplateInfo.baseTemplateDir());
        String saveName = baseTemplateInfo.fileName() + ".docx";
        Path outputFilePath = Paths.get(baseTemplateInfo.baseTemplateDir(), saveName);

        try (InputStream inputStream = getClass().getResourceAsStream(baseTemplateInfo.yamlFilePath())) {
            generateDocument(outputFilePath, new YamlConfig(inputStream));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initOutputDirectory(String outputDir) {
        try {
            Files.createDirectories(Paths.get(outputDir));
        }
        catch (IOException e) {
            throw new RuntimeException("Не удалось создать директорию для шаблонов", e);
        }
    }

    private void generateDocument(Path outputFilePath, YamlConfig yamlConfig) {
        try (InputStream inputStream = getClass().getResourceAsStream(commonTemplatePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("Общий шаблон для документов не найден по пути: " + commonTemplatePath);
            }

            try (XWPFDocument document = new XWPFDocument(inputStream);
                 FileOutputStream out = new FileOutputStream(outputFilePath.toFile())) {
                super.processDocument(document, yamlConfig);
                document.write(out);
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Ошибка при генерации шаблона: " + outputFilePath, e);
        }
    }

    @Override
    protected void applyReplacement(XWPFRun run, Matcher matcher, YamlConfig yamlConfig, StringBuilder result) {
        String key = matcher.group(1); // ключ без $()

        String value = yamlConfig.getString(key);

        if (value != null && !value.isEmpty()) {
            // TODO: убрать костыль (нужно добавить в .yml флаг для обозначения покраски)
            if (!key.equals("компетенцииЗаданияПрактики")) {
                run.setTextHighlightColor("white");
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        else {
            matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
        }
    }

    @Override
    protected void applyTableReplacement(XWPFRun run, XWPFTable table, Matcher matcher, YamlConfig yamlConfig, StringBuilder result) {
        TableProcessor tableProcessor = new TableProcessor();
        String key = matcher.group(1); // ключ без $()

        if (key.equals("компетенции")) {
            tableProcessor.removeMarkerRow(table, "компетенции");
            tableProcessor.addCompetencies(table, yamlConfig.getCompetencies());
            return;
        }

        String value = yamlConfig.getString(key);

        if (value != null && !value.isEmpty()) {
            // TODO: убрать костыль (нужно добавить в .yml флаг для обозначения покраски)
            if (!matcher.group(1).equals("компетенцииЗаданияПрактики")) {
                run.setTextHighlightColor("white");
            }
            matcher.appendReplacement(result, Matcher.quoteReplacement(value));
        }
        else {
            matcher.appendReplacement(result, Matcher.quoteReplacement(matcher.group(0)));
        }
    }
}
