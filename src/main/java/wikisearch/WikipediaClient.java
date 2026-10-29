package wikisearch;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

public class WikipediaClient {
    private static final String SEARCH_URL =
            "https://ru.wikipedia.org/w/api.php?action=query&list=search&utf8=&format=json&srlimit=10&srsearch=";

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public String search(String query) throws WikipediaException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SEARCH_URL + encodedQuery))
                .timeout(Duration.ofSeconds(10))
                .header("User-Agent", "WikipediaSearchStudentApp/1.0")
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new WikipediaException("Сервер вернул ошибку, код " + response.statusCode());
            }
            return response.body();
        } catch (IOException e) {
            throw new WikipediaException("Не удалось связаться с Википедией. Проверьте интернет.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WikipediaException("Запрос был прерван.");
        }
    }
}