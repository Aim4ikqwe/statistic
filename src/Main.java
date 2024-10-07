import java.io.*;
import java.nio.file.*;
import java.util.*;

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

            // Подсчет частоты появления каждого символа без использования лямбд
            Map<Character, Long> charFrequency = new HashMap<>();
            for (int i = 0; i < content.length(); i++) {
                char character = content.charAt(i);
                charFrequency.put(character, charFrequency.getOrDefault(character, 0L) + 1);
            }

            // Определение наиболее часто встречающегося слова
            Map<String, Long> wordFrequency = new HashMap<>();
            for (String word : words) {
                String lowerCaseWord = word.toLowerCase();
                wordFrequency.put(lowerCaseWord, wordFrequency.getOrDefault(lowerCaseWord, 0L) + 1);
            }
            String mostFrequentWord = null;
            long maxCount = 0;
            for (Map.Entry<String, Long> entry : wordFrequency.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostFrequentWord = entry.getKey();
                }
            }

            // Разделитель для визуализации вывода
            String separator = new String(new char[40]).replace("\0", "-");

            // Вывод информации в консоль
            System.out.println(separator);
            System.out.println("Результаты анализа текста:");
            System.out.println(separator);
            System.out.println("Количество символов в тексте: " + totalCharacters);
            System.out.println("Количество символов без пробелов: " + charactersWithoutSpaces);
            System.out.println("Количество слов: " + wordCount);
            System.out.println("Наиболее частое слово: \"" + mostFrequentWord + "\"");
            System.out.println(separator);

            // Вывод частоты символов
            System.out.println("Частота символов:");
            for (Map.Entry<Character, Long> entry : charFrequency.entrySet()) {
                System.out.println("Символ '" + entry.getKey() + "' встречается: " + entry.getValue() + " раз(а)");
            }
            System.out.println(separator);

            // Запись результатов в файл
            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath));
            writer.write("Результаты анализа текста:\n");
            writer.write("Количество символов в тексте: " + totalCharacters + "\n");
            writer.write("Количество символов без пробелов: " + charactersWithoutSpaces + "\n");
            writer.write("Количество слов: " + wordCount + "\n");
            writer.write("Наиболее частое слово: \"" + mostFrequentWord + "\"\n");
            writer.write("Частота символов:\n");

            // Используем обычный цикл для записи частоты символов в файл
            for (Map.Entry<Character, Long> entry : charFrequency.entrySet()) {
                writer.write(String.format("Символ '%c' встречается: %d раз(а)%n", entry.getKey(), entry.getValue()));
            }
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
