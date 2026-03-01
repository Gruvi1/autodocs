package ru.nsu.astakhov.autodocs.document;

import com.github.petrovich4j.Case;
import com.github.petrovich4j.Gender;
import com.github.petrovich4j.NameType;
import com.github.petrovich4j.Petrovich;

public class RussianWordDecliner {
    private final Petrovich petrovich;

    public RussianWordDecliner() {
        petrovich = new Petrovich();
    }

    public Gender getGenderByPatronymic(String patronymic) {
        return petrovich.gender(patronymic, Gender.Both);
    }

    public String getStudentFormByGender(Gender gender) {
        final String maleStudent = "Обучающийся";
        final String femaleStudent = "Обучающаяся";

        return gender == Gender.Male ? maleStudent : femaleStudent;
    }

    public String getGenitiveStudentFormByGender(Gender gender) {
        final String maleGenitiveStudent = "Обучающегося";
        final String femaleGenitiveStudent = "Обучающейся";

        return gender == Gender.Male ? maleGenitiveStudent : femaleGenitiveStudent;
    }

    public String getFullNameInGenitiveCase(String fullName, Gender gender) {
        Case correctCase = Case.Genitive;

        String[] nameParts = fullName.split(" ");

        if (nameParts.length == 3) {
            String surname = petrovich.say(nameParts[0], NameType.LastName, gender, correctCase);
            String name = petrovich.say(nameParts[1], NameType.FirstName, gender, correctCase);
            String patronymic = petrovich.say(nameParts[2], NameType.PatronymicName, gender, correctCase);

            return surname + ' ' + name + ' ' + patronymic;
        }
        else if (nameParts.length == 2) {
            String surname = petrovich.say(nameParts[0], NameType.LastName, gender, correctCase);
            String name = petrovich.say(nameParts[1], NameType.FirstName, gender, correctCase);

            return surname + ' ' + name;
        }
        else if (nameParts.length == 1) {
            return petrovich.say(nameParts[0], NameType.LastName, gender, correctCase);
        }
        else {
            return "";
        }
    }
}
