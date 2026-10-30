package wikisearch;

public class Main {
    public static void main(String[] args) {
        ConsoleInput input = new ConsoleInput();
        WikipediaClient client = new WikipediaClient();
        ResponseParser parser = new ResponseParser();
        try {
            String json = client.search(input.readQuery());
            for (SearchResult result : parser.parse(json)) {
                System.out.println(result.getTitle() + " | " + result.getArticleUrl());
            }
        } catch (WikipediaException e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
}