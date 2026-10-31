package wikisearch;

public class SearchResult {
    private final String title;
    private final long pageId;
    private final String snippet;

    public SearchResult(String title, long pageId, String snippet) {
        this.title = title;
        this.pageId = pageId;
        this.snippet = snippet;
    }

    public String getTitle() {
        return title;
    }

    public long getPageId() {
        return pageId;
    }

    public String getSnippet() {
        return snippet;
    }
}