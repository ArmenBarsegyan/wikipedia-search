package wikisearch;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Properties;

public class WikipediaClient {
    private static final String CONFIG_FILE = "config.properties";

    private final String searchUrl;
    private final String articleUrl;
    private final int limit;
    private final Duration timeout;
    private final String userAgent;
    private final HttpClient httpClient;

    public WikipediaClient() throws WikipediaException {
        Properties config = loadConfig();
        this.searchUrl = getString(config, "api.url");
        this.articleUrl = getString(config, "article.url");
        this.limit = getInt(config, "search.limit");
        this.timeout = Duration.ofSeconds(getInt(config, "timeout.seconds"));
        this.userAgent = getString(config, "user.agent");
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(timeout)
                .build();
    }

    public String search(String query) throws WikipediaException {
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String url = searchUrl + "&srlimit=" + limit + "&srsearch=" + encodedQuery;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(timeout)
                .header("User-Agent", userAgent)
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

    public String getArticleUrl(long pageId) {
        return articleUrl + pageId;
    }

    private Properties loadConfig() throws WikipediaException {
        Properties config = new Properties();
        try (InputStream stream = WikipediaClient.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (stream == null) {
                throw new WikipediaException("Не найден файл настроек " + CONFIG_FILE);
            }
            config.load(new InputStreamReader(stream, StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new WikipediaException("Не удалось прочитать файл настроек " + CONFIG_FILE);
        }
        return config;
    }

    private String getString(Properties config, String key) throws WikipediaException {
        String value = config.getProperty(key);
        if (value == null || value.isBlank()) {
            throw new WikipediaException("В настройках нет параметра " + key);
        }
        return value.trim();
    }

    private int getInt(Properties config, String key) throws WikipediaException {
        try {
            return Integer.parseInt(getString(config, key));
        } catch (NumberFormatException e) {
            throw new WikipediaException("Параметр " + key + " должен быть числом");
        }
    }
}