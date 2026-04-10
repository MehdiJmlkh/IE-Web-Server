package ir.ac.ut.ece.ie.services;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.exceptions.NotUniqueTitleException;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

public class ArticleService {
    private final ArticleRepository articleRepository = ArticleRepository.getInstance();

    public void addArticle(AddArticleRequest request) throws NotUniqueTitleException {
        var article = articleRepository.getArticles().stream()
                .filter(a -> a.getTitle().equals(request.getTitle()))
                .findFirst()
                .orElse(null);

        if (article != null) {
            throw new NotUniqueTitleException();
        }

        Article newArticle = new Article(request.getTitle(), request.getAbs(), request.getBody(), request.getYear());
        newArticle.setCitationIds(request.getCitationIds());
        articleRepository.addArticle(newArticle);
    }

    public void filterArticles(FilterArticlesRequest request) {
        articleRepository.setFilter(request.getSearchInput());
    }
}
