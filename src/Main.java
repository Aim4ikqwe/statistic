import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрос пути к файлу от пользователя. Если ввод пустой, используется путь по умолчанию.
        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine().trim();
        String outputFilePath = "Result.svc";  // Имя файла для записи результата

        try {
            // Чтение содержимого файла в строку
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Подсчет общего количества символов в тексте
            int totalCharacters = content.length();

            // Подсчет количества символов, исключая пробелы
            int charactersWithoutSpaces = content.replace(" ", "").length();

            // Подсчет количества слов в тексте
            String[] words = content.trim().split("\\s+");
            int wordCount = words.length;

            // Подсчет частоты появления каждого символа
            Map<Character, Long> charFrequency = content.chars()
                    .mapToObj(c -> (char) c)
                    .collect(Collectors.groupingBy(c -> c, Collectors.counting()));

            // Определение наиболее часто встречающегося слова
            Map<String, Long> wordFrequency = Arrays.stream(words)
                    .collect(Collectors.groupingBy(w -> w.toLowerCase(), Collectors.counting()));
            String mostFrequentWord = Collections.max(wordFrequency.entrySet(), Map.Entry.comparingByValue()).getKey();

            // Разделитель для визуализации вывода
            String separator = new String(new char[40]).replace("\0", "-");

            // Вывод информации в консоль через foreach
            System.out.println(separator);
            System.out.println("Результаты анализа текста:");
            System.out.println(separator);
            System.out.println("Количество символов в тексте: " + totalCharacters);
            System.out.println("Количество символов без пробелов: " + charactersWithoutSpaces);
            System.out.println("Количество слов: " + wordCount);
            System.out.println("Наиболее частое слово: \"" + mostFrequentWord + "\"");
            System.out.println(separator);

            // Вывод частоты символов через foreach
            System.out.println("Частота символов:");
            for (int c : content.chars().toArray()) {
                char character = (char) c;
                charFrequency.put(character, charFrequency.getOrDefault(character, 0L) + 1);
            }
            System.out.println(separator);

            // Запись результатов в файл через foreach
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            writer.write("Результаты анализа текста:\n");
            writer.write("Количество символов в тексте: " + totalCharacters + "\n");
            writer.write("Количество символов без пробелов: " + charactersWithoutSpaces + "\n");
            writer.write("Количество слов: " + wordCount + "\n");
            writer.write("Наиболее частое слово: \"" + mostFrequentWord + "\"\n");
            writer.write("Частота символов:\n");

            // Используем foreach для записи частоты символов в файл
            charFrequency.forEach((ch, freq) -> {
                try {
                    writer.write(String.format("Символ '%c' встречается: %d раз(а)%n", ch, freq));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            writer.write(separator + "\n");
            writer.close();

            // Уведомление об успешной записи результатов в файл
            System.out.println("Результаты записаны в файл: " + outputFilePath);

        } catch (IOException e) {
            // Обработка ошибки при чтении файла
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
}
