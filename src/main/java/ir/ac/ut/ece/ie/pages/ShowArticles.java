package ir.ac.ut.ece.ie.pages;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import ir.ac.ut.ece.ie.services.ArticleService;
import ir.ac.ut.ece.ie.services.Article;
import java.util.List;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class ShowArticles {

    public byte[] pageBody(Map<String, String> params) {

        List<Article> articles = ArticleService.getInstance().getFilteredArticles();

        StringBuilder articles_html = new StringBuilder();

        for (Article a : articles) {
            String slug = a.getTitle().toLowerCase().replaceAll(" ", "-");
            String abstractSnippet = a.getAbstract().length() > 400
                    ? a.getAbstract().substring(0, 400) + "..."
                    : a.getAbstract();

            articles_html.append(String.format("""
                <div class="article">
                  <a href="ArticleDetails?title=%s" class="article__header">%s</a>
                  <p class="article__abstract">%s</p>
                </div>
                """, slug, a.getTitle(), abstractSnippet));
        }

        String html = loadTemplate("showArticles.html")
                .replace("{articles}", articles_html.toString());
        return html.getBytes();
    }
}

