package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.services.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.util.List;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class ArticleDetails {
    public byte[] pageBody(Map<String, String> params) {
        String title = params.get("title");

        List<Article> articles = ArticleService.getInstance().getArticles();

        Article article = articles.stream()
                .filter(a -> a.getTitle().toLowerCase().replaceAll(" ", "-").equals(title))
                .findFirst()
                .orElse(null);

        String html = loadTemplate("articleDetails.html")
                .replace("{title}", article.getTitle())
                .replace("{year}",  String.valueOf(article.getYear()))
                .replace("{abstract}", article.getAbstract())
                .replace("{body}", article.getBody());
        return html.getBytes();
    }
}
