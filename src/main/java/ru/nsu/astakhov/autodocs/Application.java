package ru.nsu.astakhov.autodocs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.ui.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.view.Window;
import ru.nsu.astakhov.autodocs.utils.Ini;

import javax.swing.*;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final Controller controller;
    private final Window window;

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
        SwingUtilities.invokeLater(() -> window.setVisible(true));
        controller.updateTable(null);
    }
}
