package ru.nsu.astakhov.autodocs;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.nsu.astakhov.autodocs.model.*;
import ru.nsu.astakhov.autodocs.model.StudentDto;
import ru.nsu.astakhov.autodocs.service.StudentService;
import ru.nsu.astakhov.autodocs.integration.google.GoogleSheetsService;
import ru.nsu.astakhov.autodocs.utils.configs.ConfigManager;
import ru.nsu.astakhov.autodocs.utils.ini.Ini;

@RequiredArgsConstructor
@SpringBootApplication
public class Application implements CommandLineRunner {
    private final StudentService studentService;
    private final GoogleSheetsService googleSheetsService;

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

//        SwingUtilities.invokeLater(() -> new Window().setVisible(true));
//
//        try {
//            List<List<Object>> data = googleSheetsService.readRange("3 курс б.");
//            for (List<Object> line : data) {
//                System.out.println(line);
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        StudentDto dto = new StudentDto(
                null,
                "Авдонина Лада Вадимовна",
                Course.THIRD,
                "l.avdonina@g.nsu.ru",
                null,
                EduProgram.fromValue("09.03.01 Информатика и вычислительная техника"),
                "23202",
                Specialization.fromValue("Программная инженерия и компьютерные науки"),
                null,
                null,
                "Пальчунов Дмитрий Евгеньевич",
                null,
                null,
                null,
                null,
                InternshipType.fromValue("Учебная практика (научно-исследовательская работа (получение первичных навыков научно-исследовательской работы))"),
                new Supervisor(
                        "Пальчунов Дмитрий Евгеньевич",
                        "зав. кафедрой",
                        "д.ф.-м.н.",
                        "доцент"
                ),
                "ИМ СО РАН, Лаборатория теории вычислимости и прикладной логики",
                new Supervisor(
                        "Пальчунов Дмитрий Евгеньевич",
                        "зав. кафедрой",
                        "д.ф.-м.н.",
                        "доцент"
                ),
                new Supervisor(
                        "Пальчунов Дмитрий Евгеньевич",
                        "в.н.с.",
                        "д.ф.-м.н.",
                        "доцент"
                ),
                "ФГБУН Институт математики им. С. Л. Соболева СО РАН, Лаборатория теории вычислимости и прикладной логики, 630090, Новосибирская обл., Новосибирск, пр. Академика Коптюга, 15",
                "ИМ СО РАН"
        );
        studentService.create(dto);
    }
}
