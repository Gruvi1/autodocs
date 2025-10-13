package ru.nsu.astakhov.autodocs.core.utils.ini;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Ini {
    private final Map<String, Map<String, String>> config;

    private String lastSection;

    public Ini(String filePath) {
        config = new HashMap<>();

        this.lastSection = null;

        parseFile(filePath);
    }

    public void print() { // TODO: удалить
        for (String sectionName : config.keySet()) {
            System.out.println('[' + sectionName + ']');
            Map<String, String> section = config.get(sectionName);
            for (String key : section.keySet()) {
                System.out.println(key + " : " + section.get(key));
            }
            System.out.println();
        }
    }

    public String getValue(String sectionName, String key) {
        return config.get(sectionName).get(key);
    }

    public void addNewFile(String filePath) {
        parseFile(filePath);
    }

    private void parseFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream(filePath))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isBlank() || line.charAt(0) == ';' ) {
                    continue;
                }
                parseLine(line);
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void parseLine(String line) {
        if (line.charAt(0) == '[') {
            final int startIndex = 1;
            final int endIndex = line.length() - 1;

            String sectionName = line.substring(startIndex, endIndex);
            config.putIfAbsent(sectionName, new HashMap<>());
            lastSection = sectionName;
        }
        else {
            if (lastSection == null) {
                System.err.println("Некорректный INI файл: Отсутствует секция");
                return;
            }

            String[] lineParts = line.replace(" ", "").split("=");

            if (lineParts.length != 2) {
                System.err.println("Некорректная строка в INI файле: " + line);
                return;
            }

            Map<String, String> section = config.get(lastSection);

            String key = lineParts[0];
            String value = lineParts[1];

            section.put(key, value);
        }
    }
}
