package ru.nsu.astakhov.autodocs.ui.configs;

import ru.nsu.astakhov.autodocs.utils.Ini;

import java.awt.*;

public class ConfigManager {
    private static Ini ini = null;

    public static void setIni(Ini ini) {
        if (ConfigManager.ini != null) {
            throw new IllegalStateException("Ini already initialized");
        }
        ConfigManager.ini = ini;
    }

    public static boolean isDarkTheme() {
        ConfigConstants configConstants = ConfigConstants.THEME;
        String theme = ini.getValue(configConstants.getSection(), configConstants.getSettingName());
        return theme.equals(ThemeType.DARK.getValue());
    }

    public static String getSetting(ConfigConstants configConstants) {
        if (configConstants.getSection().equals(PrivateConstants.TAG_COLOR.getValue())) {
            return getColor(configConstants);
        }
        return ini.getValue(configConstants.getSection(), configConstants.getSettingName());
    }

    public static Color parseHexColor(String hexColor) {
        if (hexColor == null || hexColor.isBlank()) {
            return Color.BLACK;
        }
        try {
            String typedHexColor;
            if (hexColor.startsWith("0x")) {
                typedHexColor = hexColor.substring(2);
            }
            else if (hexColor.startsWith("#")) {
                typedHexColor = hexColor.substring(1);
            }
            else {
                typedHexColor = hexColor;
            }
            int HEX_SYSTEM = 16;
            int rgb = Integer.parseInt(typedHexColor, HEX_SYSTEM);
            return new Color(rgb);
        }
        catch (NumberFormatException e) {
            System.err.println("Некорректный формат цвета: " + hexColor);
            return Color.BLACK;
        }
    }

    private static String getColor(ConfigConstants configConstants) {
        String theme = ConfigManager.getSetting(ConfigConstants.THEME);
        String section = theme.equals(PrivateConstants.DARK_THEME.getValue()) ? PrivateConstants.DARK_COLORS_SECTION.getValue() : PrivateConstants.LIGHT_COLORS_SECTION.getValue();
        return ini.getValue(section, configConstants.getSettingName());
    }
}
