package ru.nsu.astakhov.autodocs;

import ru.nsu.astakhov.autodocs.core.utils.ConfigManager;
import ru.nsu.astakhov.autodocs.core.utils.ini.Ini;
import ru.nsu.astakhov.autodocs.core.view.Window;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        final String configPath = "/config/default_config.ini";
        final String constantsPath = "/config/constants.ini";

        Ini ini = new Ini(configPath);
        ini.addNewFile(constantsPath);

        ConfigManager.setIni(ini);

        SwingUtilities.invokeLater(() -> new Window().setVisible(true));

    }
}
