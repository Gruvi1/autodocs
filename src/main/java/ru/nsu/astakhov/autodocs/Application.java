package ru.nsu.astakhov.autodocs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.view.Window;
import ru.nsu.astakhov.autodocs.utils.Ini;

import javax.swing.*;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final StudentService studentService;

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");

        final String configPath = "/config/default_config.ini";
        final String constantsPath = "/config/constants.ini";

        Ini ini = new Ini(configPath);
        ini.addNewFile(constantsPath);

        ConfigManager.setIni(ini);

        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args)  {
        studentService.scanAllData();
//        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
    }
}
