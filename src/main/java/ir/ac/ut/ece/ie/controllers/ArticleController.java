package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ArticleController {
    public static void addArticle(AddArticleRequest request, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Article article = new Article(request.getTitle(), request.getAbs(), request.getBody(), 2000);
        ArticleService.getInstance().addArticle(article);
        httpResponse.writePage("ShowArticles", null);
    }


    public static void filterArticles(Map<String, String> payload, HttpResponse httpResponse) throws IOException {
        ArticleService.getInstance().setFilter(payload.get("search"));
        httpResponse.sendNoContentResponse();
    }
}
