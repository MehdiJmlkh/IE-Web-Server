package ir.ac.ut.ece.ie.services;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

public class ArticleService {
    public static void addArticle(AddArticleRequest request) {
        Article article = new Article(request.getTitle(), request.getAbs(), request.getBody(), 2000);
        article.setCitationIds(request.getCitationIds());
        ArticleRepository.getInstance().addArticle(article);
    }

    public static void filterArticles(FilterArticlesRequest request) {
        ArticleRepository.getInstance().setFilter(request.getSearchInput());
    }
}
