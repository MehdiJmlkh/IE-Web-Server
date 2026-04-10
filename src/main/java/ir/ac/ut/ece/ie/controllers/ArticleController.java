package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.exceptions.NotUniqueTitleException;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ArticleController {
    private final ArticleService articleService = new ArticleService();

    public HttpResponse addArticle(AddArticleRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        try {
            articleService.addArticle(request);
        } catch (NotUniqueTitleException e) {
            return HttpResponse.badRequest("An article with this title already exists.");
        }
        return HttpResponse.created().dynamicContent("ShowArticles", null);
    }

    public HttpResponse filterArticles(FilterArticlesRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        articleService.filterArticles(request);
        return HttpResponse.ok().dynamicContent("ShowArticles", null);
    }
}
