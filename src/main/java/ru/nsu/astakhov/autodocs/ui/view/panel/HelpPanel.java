package ru.nsu.astakhov.autodocs.ui.view.panel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.astakhov.autodocs.ui.controller.Controller;
import ru.nsu.astakhov.autodocs.ui.controller.handler.HelpPanelEventHandler;
import ru.nsu.astakhov.autodocs.ui.view.component.CustomLabel;
import ru.nsu.astakhov.autodocs.ui.view.component.RoundedButton;
import ru.nsu.astakhov.autodocs.ui.view.font.FontLoader;
import ru.nsu.astakhov.autodocs.ui.view.font.FontType;

import javax.swing.*;
import java.awt.*;
import java.util.List;


@Component
@Slf4j
public class HelpPanel extends Panel {
    public HelpPanel(Controller controller) {
        controller.addListener(this);
        setEventHandler(new HelpPanelEventHandler());

        configurePanel();
    }


    @Override
    protected void configurePanel() {
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(createHelp());
        scrollPane.getVerticalScrollBar().setUnitIncrement(smallGap);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        setBackground(focusColor);

        scrollPane.setBackground(focusColor);

        scrollPane.setBorder(BorderFactory.createEmptyBorder(smallGap, smallGap, smallGap, smallGap));

        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void onTableUpdate(String updateStatus) {

    }

    @Override
    public void onDocumentGeneration(String generateStatus) {

    }

    private JPanel createHelp() {
        JPanel helpPanel = new JPanel();
        helpPanel.setLayout(new BoxLayout(helpPanel, BoxLayout.Y_AXIS));
//        helpPanel.setOpaque(false);
        helpPanel.setBackground(focusColor);

        JLabel header = new CustomLabel("Создание шаблона", true);
        header.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, titleTextSize));
        helpPanel.add(header);
        helpPanel.add(Box.createVerticalStrut(smallGap));

        helpPanel.add(new CustomLabel("• Для создания шаблона требуется вставить ключевые слова в нужные места документа"));
        helpPanel.add(new CustomLabel("• Ключевые слова обязательно вставлять полностью (со скобками и знаком доллара)"));
        helpPanel.add(new CustomLabel("• При генерации ключевое слово заменится на конкретное значение для студента с сохранением форматирования"));
        helpPanel.add(Box.createVerticalStrut(smallGap));

        JLabel placeholderHeader = new CustomLabel("Ключевые слова для подстановки (нажмите, чтобы скопировать)", true);
        placeholderHeader.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, textSize));
        helpPanel.add(placeholderHeader);
        helpPanel.add(Box.createVerticalStrut(smallGap));

        java.util.List<String> helpItems = List.of(
                "$(полноеИмя)\t – ФИО студента. Пример: Иванов Иван Иванович",
                "$(курс)\t – номер курса. Пример: 3",
                "$(почта)\t – почта студента. Пример: i.ivanov@g.nsu.ru",
                "$(телефон)\t – номер телефона студента. Пример: 8-111-222-33-44",
                "$(образПрограмма)\t – образовательная программа. Пример: 09.03.01 Информатика и вычислительная техника",
                "$(группа)\t – номер группы студента. Пример: 23207",
                "$(специализация)\t – профиль обучения. Пример: Программная инженерия и компьютерные науки",
                "$(утверждениеТемы)\t – распоряжение об утверждении темы и руководителей ВКР. Пример: 1111 от 01.01.2001",
                "$(корректировкаТемы)\t – распоряжение о корректировке темы и руководителей ВКР. Пример: 1111 от 01.01.2001",
                "$(фактический)\t – ФИО того, с кем по факту работает. Пример: Иванов Иван Иванович",
                "$(соруководительВКР)\t – ФИО соруководителя ВКР. Пример: Иванов Иван Иванович",
                "$(консультантВКР)\t – ФИО консультанта ВКР. Пример: Иванов Иван Иванович",
                "$(темаВКР)\t – название темы ВКР. Пример: Разработка системной системы для систематизирования систем",
                "$(рецензент)\t – рецензент (ФИО, уч. степень, должность, место работы). Пример: ?",
                "$(видПрактики)\t – название вида практики. Пример: Учебная практика\n(научно-исследовательская работа (получение первичных навыков научно-исследовательской работы))",
                "$(руководительВКР.имя)\t – ФИО руководителя ВКР. Пример: Иванов Иван Иванович",
                "$(руководительВКР.должность)\t – должность руководителя ВКР. Пример: зав. каф.",
                "$(руководительВКР.степень)\t – учёная степень руководителя ВКР. Пример: д.ф.-м.н.",
                "$(руководительВКР.звание)\t – учёное звание руководителя ВКР. Пример: доцент",
                "$(полноеИмяОрганизации)\t – название организации, структурного подразделения номер кабинета\nПример: ИМ СО РАН, Лаборатория теории вычислимости и прикладной логики",
                "$(руководительНГУ.имя)\t – ФИО руководителя НГУ. Пример: Иванов Иван Иванович",
                "$(руководительНГУ.должность)\t – должность руководителя НГУ. Пример: зав. каф.",
                "$(руководительНГУ.степень)\t – учёная степень руководителя НГУ. Пример: д.ф.-м.н.",
                "$(руководительНГУ.звание)\t – учёное звание руководителя НГУ. Пример: доцент",
                "$(руководительОрганизации.имя)\t – ФИО руководителя от организации. Пример: Иванов Иван Иванович",
                "$(руководительОрганизации.должность)\t – должность руководителя от организации. Пример: в.н.с.",
                "$(руководительОрганизации.степень)\t – учёная степень руководителя от организации. Пример: д.ф.-м.н.",
                "$(руководительОрганизации.звание)\t – учёное звание руководителя от организации. Пример: доцент",
                "$(актОтОрганизации)\t – распорядительный акт от института (профильной организации). Пример: \"1\" января 2001 г. №11-аа",
                "$(местоПрактикиПолностью)\t – полное название места практики\nПример: ФГБУН Институт математики им. С. Л. Соболева СО РАН, Лаборатория теории вычислимости и прикладной логики,\n630090, Новосибирская обл., Новосибирск, пр. Академика Коптюга, 15",
                "$(наименованиеОрганизации)\t – короткое название организации. Пример: ИМ СО РАН",
                "$(полОбучающегося)\t – слово \"Обучающегося\"/\"Обучающейся\" в зависимости от пола студента",
                "$(полноеИмяРодительный)\t – ФИО студента в родительном падеже. Пример: Иванова Ивана Ивановича",
                "$(полОбучающийся)\t – слово \"Обучающийся\"/\"Обучающаяся\" в зависимости от пола студента"
        );

        for (String item : helpItems) {
            helpPanel.add(createBulletPoint(item));
            helpPanel.add(Box.createVerticalStrut(smallGap));
        }

        return helpPanel;
    }

    private JPanel createBulletPoint(String text) {
        JPanel lines = new JPanel();
        lines.setLayout(new BoxLayout(lines, BoxLayout.Y_AXIS));
        lines.setOpaque(false);

        String[] parts = text.split("\t");
        if (parts.length != 2) {
            logger.error("The template word must be separated by a tab character: {}", text);
            throw new IllegalStateException("The template word must be separated by a tab character: " + text);
        }

        JPanel firstLine = new JPanel();
        firstLine.setLayout(new BoxLayout(firstLine, BoxLayout.X_AXIS));
        firstLine.setOpaque(false);

        RoundedButton placeholderButton = new RoundedButton(parts[0]);
        placeholderButton.setCopyButton();
        placeholderButton.setFont(FontLoader.loadFont(FontType.ADWAITA_SANS_REGULAR, menuTextSize));
        placeholderButton.setMargin(new Insets(0, 0, 0, 0));

        firstLine.add(placeholderButton);

        String[] tail = parts[1].split("\n");
        firstLine.add(new CustomLabel(tail[0]));
        firstLine.setAlignmentX(LEFT_ALIGNMENT);

        lines.add(firstLine);

        for (int i = 1; i < tail.length; i++) {
            lines.add(new CustomLabel(tail[i]));
        }

        lines.setAlignmentX(LEFT_ALIGNMENT);

        return lines;
    }
}