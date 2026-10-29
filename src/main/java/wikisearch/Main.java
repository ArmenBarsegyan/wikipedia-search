package wikisearch;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        WikipediaClient client = new WikipediaClient();
        try {
            String json = client.search(input.readQuery());
            System.out.println(json);
        } catch (WikipediaException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}