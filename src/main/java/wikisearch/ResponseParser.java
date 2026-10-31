package wikisearch;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class ResponseParser {
    public List<SearchResult> parse(String json) throws WikipediaException {
        List<SearchResult> results = new ArrayList<>();
        try {
            JsonObject root = JsonParser.parseString(json).getAsJsonObject();
            if (root.has("error")) {
                throw new WikipediaException("Википедия вернула ошибку.");
            }
            JsonArray found = root.getAsJsonObject("query").getAsJsonArray("search");
            for (JsonElement element : found) {
                JsonObject item = element.getAsJsonObject();
                String title = item.get("title").getAsString();
                long pageId = item.get("pageid").getAsLong();
                String snippet = cleanSnippet(item.get("snippet").getAsString());
                results.add(new SearchResult(title, pageId, snippet));
            }
        } catch (JsonParseException | IllegalStateException | NullPointerException e) {
            throw new WikipediaException("Не удалось разобрать ответ сервера.");
        }
        return results;
    }

    private String cleanSnippet(String snippet) {
        return snippet.replaceAll("<[^>]*>", "")
                .replace("&quot;", "\"")
                .replace("&#039;", "'")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&amp;", "&");
    }
}