//Урок 3. Продвинутая работа с исключениями в Java
//        Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
//        разделенные пробелом:
//        Фамилия Имя Отчество датарождения номертелефона пол
//        Форматы данных:
//        фамилия, имя, отчество - строки
//        дата_рождения - строка формата dd.mm.yyyy
//        номер_телефона - целое беззнаковое число без форматирования
//        пол - символ латиницей f или m.
//        Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
//        вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//        Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
//        Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
//        Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано,
//        пользователю выведено сообщение с информацией, что именно неверно.
//        Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
//        в него в одну строку должны записаться полученные данные, вида
//       <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//        Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//        Не забудьте закрыть соединение с файлом.
//        При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите данные в формате: Фамилия Имя Отчество дата_рождения номер_телефона пол: ");
        String input = scanner.nextLine();

        String[] data = input.split(" ");
        if (data.length != 6) {
            System.err.println("Ошибка: неверное количество данных");
            return;
        }

        String surname = data[0];
        String name = data[1];
        String patronymic = data[2];
        LocalDate birthDate;
        try {
            birthDate = LocalDate.parse(data[3], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        } catch (Exception e) {
            System.err.println("Ошибка: неверный формат даты рождения");
            return;
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(data[4]);
        } catch (NumberFormatException e) {
            System.err.println("Ошибка: неверный формат номера телефона");
            return;
        }
        char gender;
        if (data[5].equals("f") || data[5].equals("m")) {
            gender = data[5].charAt(0);
        } else {
            System.err.println("Ошибка: неверный формат пола");
            return;
        }

        String fileName = surname + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(surname + name + patronymic + birthDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                    + " " + phoneNumber + gender + "n");
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл: " + e.getMessage());
            return;
        }

        System.out.println("Данные успешно записаны в файл " + fileName);
    }
}
