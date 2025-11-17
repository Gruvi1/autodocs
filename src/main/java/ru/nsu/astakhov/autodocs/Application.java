package ru.nsu.astakhov.autodocs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.Window;
import ru.nsu.astakhov.autodocs.util.Ini;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Controller controller;
    private final Window window;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        System.setProperty("sun.java2d.uiScale.enabled", "false");

        initConfigManager();

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args)  {
        SwingUtilities.invokeLater(() -> window.setVisible(true));
        controller.updateTable(window);
    }

    private static void initConfigManager() {
        Properties props = new Properties();
        try (InputStream is = Application.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (is != null) {
                props.load(is);
            }
        }
        catch (IOException e) {
            throw new IllegalStateException("Не удалось загрузить application.properties", e);
        }

        String configPath = props.getProperty("app.config.path");
        String constantsPath = props.getProperty("app.constants.path");

        Ini ini = new Ini(configPath);
        ini.addNewFile(constantsPath);
        ConfigManager.setIni(ini);

    }
}
