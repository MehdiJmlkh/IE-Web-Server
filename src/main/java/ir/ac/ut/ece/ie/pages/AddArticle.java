package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class AddArticle {
    public byte[] pageBody(Map<String, String> params) {
        List<Article> articles = ArticleRepository.getInstance()
                .getArticles().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        StringBuilder articles_html = new StringBuilder();

        for (Article a : articles) {
            articles_html.append(String.format("""
                  <div class="article">
                    <input class="form-check-input article__checkbox" type="checkbox" name="citations[] value=%s"/>
                    <label class="form-check-label">
                        <a href="ArticleDetails?title=%s" class="article__header">%s</a>
                    </label>
                  </div>
                """, a.getId(), a.getSlug(), a.getTitle()));
        }
        int currentYear = java.time.Year.now().getValue();

        String html = loadTemplate("addArticle.html")
                .replace("{currentYear}", String.valueOf(currentYear))
                .replace("{articles}", articles_html.toString());
        return html.getBytes();
    }
}
