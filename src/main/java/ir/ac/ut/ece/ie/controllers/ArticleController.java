package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class ArticleController {
    public static void addArticle(Map<String, String> payload, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        Article article = new Article(payload.get("title"), payload.get("abstract"), payload.get("body"), 2000);
        ArticleService.getInstance().addArticle(article);
        httpResponse.writePage("ShowArticles", null);
    }


    public static void filterArticles(Map<String, String> payload, HttpResponse httpResponse) throws IOException {
        ArticleService.getInstance().setFilter(payload.get("search"));
        httpResponse.sendNoContentResponse();
    }
}
