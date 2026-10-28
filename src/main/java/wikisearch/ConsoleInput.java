package wikisearch;

import java.util.Scanner;

public class ConsoleInput {
    public static final String EXIT_COMMAND = "exit";

    private final Scanner scanner = new Scanner(System.in);

    public String readQuery() {
        while (true) {
            System.out.print("Введите запрос (" + EXIT_COMMAND + " для выхода): ");
            String line = readLine();
            if (line == null) {
                return EXIT_COMMAND;
            }
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Запрос не может быть пустым.");
        }
    }

    public int readChoice(int max) {
        while (true) {
            System.out.print("Выберите статью (1-" + max + ", 0 - новый поиск): ");
            String line = readLine();
            if (line == null) {
                return 0;
            }
            int number;
            try {
                number = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Введите число от 0 до " + max + ".");
                continue;
            }
            if (number >= 0 && number <= max) {
                return number;
            }
            System.out.println("Число должно быть от 0 до " + max + ".");
        }
    }

    private String readLine() {
        if (!scanner.hasNextLine()) {
            return null;
        }
        return scanner.nextLine().trim();
    }
}