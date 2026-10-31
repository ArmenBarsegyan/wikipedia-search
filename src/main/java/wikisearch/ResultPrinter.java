package wikisearch;

import java.util.List;

public class ResultPrinter {
    public void printResults(List<SearchResult> results) {
        System.out.println();
        for (int i = 0; i < results.size(); i++) {
            SearchResult result = results.get(i);
            System.out.println((i + 1) + ". " + result.getTitle());
            System.out.println("   " + result.getSnippet());
        }
        System.out.println();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }
}