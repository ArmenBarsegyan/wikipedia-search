package wikisearch;

import java.util.List;

public class App {
    private final ConsoleInput input = new ConsoleInput();
    private final ResponseParser parser = new ResponseParser();
    private final ResultPrinter printer = new ResultPrinter();
    private final BrowserOpener browser = new BrowserOpener();
    private final WikipediaClient client;

    public App() throws WikipediaException {
        this.client = new WikipediaClient();
    }

    public void run() {
        while (true) {
            String query = input.readQuery();
            if (query.equalsIgnoreCase(ConsoleInput.EXIT_COMMAND)) {
                printer.printMessage("Работа завершена.");
                return;
            }
            search(query);
        }
    }

    private void search(String query) {
        try {
            String json = client.search(query);
            List<SearchResult> results = parser.parse(json);
            if (results.isEmpty()) {
                printer.printMessage("Ничего не найдено.");
                return;
            }
            printer.printResults(results);
            int choice = input.readChoice(results.size());
            if (choice == 0) {
                return;
            }
            browser.open(client.getArticleUrl(results.get(choice - 1).getPageId()));
        } catch (WikipediaException e) {
            printer.printMessage("Ошибка: " + e.getMessage());
        }
    }
}