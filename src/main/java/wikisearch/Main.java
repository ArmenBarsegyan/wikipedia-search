package wikisearch;

public class Main {
    public static void main(String[] args) {
        try {
            new App().run();
        } catch (WikipediaException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}