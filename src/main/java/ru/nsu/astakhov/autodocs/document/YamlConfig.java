package ru.nsu.astakhov.autodocs.document;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YamlConfig {
    private static final String competenciesKey = "компетенции";
    private static final String thesisCompetenciesKey = "компетенцииВКР";
    private static final String thesisCalendarTopics = "переченьПоСтепени";

    private final Map<String, Object> data;

    @SuppressWarnings("unchecked")
    public YamlConfig(InputStream inputStream) {
        Yaml yaml = new Yaml();
        Object loaded = yaml.load(inputStream);
        this.data = (loaded instanceof Map)
                ? (Map<String, Object>) loaded
                : new HashMap<>();
    }

    public String getString(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getCompetencies() {
        return (Map<String, List<String>>) data.get(competenciesKey);
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> getThesisCompetencies() {
        return (Map<String, List<String>>) data.get(thesisCompetenciesKey);
    }

    @SuppressWarnings("unchecked")
    public List<String> getCalendarTopics() {
        return (List<String>) data.get(thesisCalendarTopics);
    }
}