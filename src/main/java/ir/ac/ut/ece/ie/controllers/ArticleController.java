package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ArticleController {
    public static HttpResponse addArticle(AddArticleRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Article article = new Article(request.getTitle(), request.getAbs(), request.getBody(), 2000);
        ArticleService.getInstance().addArticle(article);
        var httpResponse = new HttpResponse();
        httpResponse.writePage("ShowArticles", null);
        return httpResponse;
    }


    public static HttpResponse filterArticles(FilterArticlesRequest request) throws IOException {
        ArticleService.getInstance().setFilter(request.getSearchInput());
        var httpResponse = new HttpResponse();
        httpResponse.noContent();
        return httpResponse;
    }
}
