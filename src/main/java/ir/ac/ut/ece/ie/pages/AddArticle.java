package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

import java.util.List;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class AddArticle {
    public byte[] pageBody(Map<String, String> params) {
        List<Article> articles = ArticleRepository.getInstance()
                .getFilteredArticles();

        StringBuilder articles_html = new StringBuilder();

        for (Article a : articles) {
            String slug = a.getTitle().toLowerCase().replaceAll(" ", "-");
            String abstractSnippet = a.getAbstract().length() > 400
                    ? a.getAbstract().substring(0, 400) + "..."
                    : a.getAbstract();

            articles_html.append(String.format("""
                  <div class="article">
                    <input class="form-check-input article__checkbox" type="checkbox" name="citations[] value=%s"/>
                    <label class="form-check-label">
                        <a href="ArticleDetails?title=%s" class="article__header">%s</a>
                    </label>
                  </div>
                """, a.getId(), slug, a.getTitle()));
        }

        String html = loadTemplate("addArticle.html")
                .replace("{articles}", articles_html.toString());
        return html.getBytes();
    }
}
