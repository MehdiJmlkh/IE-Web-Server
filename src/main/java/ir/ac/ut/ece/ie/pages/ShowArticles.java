package ir.ac.ut.ece.ie.pages;
import java.util.Map;

import ir.ac.ut.ece.ie.repositories.ArticleRepository;
import ir.ac.ut.ece.ie.entities.Article;
import java.util.List;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class ShowArticles {

    public byte[] pageBody(Map<String, String> params) {
        List<Article> articles = ArticleRepository.getInstance()
                .getFilteredArticles();

        StringBuilder articles_html = new StringBuilder();

        for (Article a : articles) {
            String abstractSnippet = a.getAbstract().length() > 400
                    ? a.getAbstract().substring(0, 400) + "..."
                    : a.getAbstract();

            articles_html.append(String.format("""
                <div class="article">
                  <a href="ArticleDetails?title=%s" class="article__header">%s</a>
                  <div>%s</div>
                  <p class="article__abstract">%s</p>
                </div>
                """, a.getSlug(), a.getTitle(), a.getYear(),abstractSnippet));
        }

        String html = loadTemplate("showArticles.html")
                .replace("{articles}", articles_html.toString());
        return html.getBytes();
    }
}

