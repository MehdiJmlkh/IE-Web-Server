package ir.ac.ut.ece.ie.services;

import java.util.ArrayList;
import java.util.List;

public class ArticleService {

    private static final ArticleService instance = new ArticleService();
    private List<Article> articles = new ArrayList<>();

    private ArticleService() {}

    public static ArticleService getInstance() {
        return instance;
    }

    public void addArticle(Article article) {
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }
}
