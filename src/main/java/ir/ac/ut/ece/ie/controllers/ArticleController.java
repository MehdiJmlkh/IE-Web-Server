package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class ArticleController {

    public HttpResponse addArticle(AddArticleRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ArticleService.addArticle(request);
        return HttpResponse.dynamicContent("ShowArticles", null);
    }

    public HttpResponse filterArticles(FilterArticlesRequest request) throws IOException {
        ArticleService.filterArticles(request);
        return HttpResponse.noContent();
    }
}
