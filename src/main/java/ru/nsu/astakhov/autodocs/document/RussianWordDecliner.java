package ru.nsu.astakhov.autodocs.document;

import com.github.petrovich4j.Case;
import com.github.petrovich4j.Gender;
import com.github.petrovich4j.NameType;
import com.github.petrovich4j.Petrovich;
import org.springframework.stereotype.Service;

@Service
public class RussianWordDecliner {
    private final Petrovich petrovich;

    public RussianWordDecliner() {
        petrovich = new Petrovich();
    }

    public Gender getGenderByPatronymic(String patronymic) {
        Gender gender = petrovich.gender(patronymic, Gender.Both);
        if (gender == Gender.Both) {
            // TODO: тут можно попросить пользователя вручную определить пол по ФИО
            throw new IllegalArgumentException("Не удалось определить пол");
        }

        return gender;
    }

    public String getStudentFormByGender(Gender gender) {
        final String maleStudent = "Обучающегося";
        final String femaleStudent = "Обучающейся";

        return gender == Gender.Male ? maleStudent : femaleStudent;
    }

    public String getFullNameInGenitiveCase(String fullName, Gender gender) {
        Case correctCase = Case.Genitive;

        String[] nameParts = fullName.split(" ");
        if (nameParts.length != 3) {
            throw new IllegalArgumentException("Некорректные ФИО");
        }

        String surname = petrovich.say(nameParts[0], NameType.LastName, gender, correctCase);
        String name = petrovich.say(nameParts[1], NameType.FirstName, gender, correctCase);
        String patronymic = petrovich.say(nameParts[2], NameType.PatronymicName, gender, correctCase);

        return surname + ' ' + name + ' ' + patronymic;
    }
}
