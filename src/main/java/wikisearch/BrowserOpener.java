package wikisearch;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;

public class BrowserOpener {
    public void open(String url) throws WikipediaException {
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            throw new WikipediaException("Открытие браузера не поддерживается. Ссылка: " + url);
        }
        try {
            Desktop.getDesktop().browse(URI.create(url));
        } catch (IOException e) {
            throw new WikipediaException("Не удалось открыть браузер. Ссылка: " + url);
        }
    }
}