package wikisearch;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        System.out.println("Запрос: " + input.readQuery());
    }
}