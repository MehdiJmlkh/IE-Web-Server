package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

import java.util.List;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class ArticleDetails {
    public byte[] pageBody(Map<String, String> params) {
        String slug = params.get("title");

        List<Article> articles = ArticleRepository.getInstance().getArticles();

        Article article = articles.stream()
                .filter(a -> a.getSlug().equals(slug))
                .findFirst()
                .orElse(null);

        StringBuilder citations_html = new StringBuilder();

        for (Integer citationId: article.getCitationIds()) {
            Article citation = ArticleRepository.getInstance().findById(citationId);
            citations_html.append(String.format("""
                  <div class="article">
                        <a href="ArticleDetails?title=%s" class="article__header">%s</a>
                  </div>
                """, citation.getSlug(), citation.getTitle()));
        }

        String html = loadTemplate("articleDetails.html")
                .replace("{title}", article.getTitle())
                .replace("{year}",  String.valueOf(article.getYear()))
                .replace("{abstract}", article.getAbstract())
                .replace("{body}", "<p>" + article.getBody().replace("\n", "</p><p>") + "</p>")
                .replace("{citations}", citations_html);
        return html.getBytes();
    }
}
