package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.services.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.util.Map;

public class ArticleController {
    public static void addArticle(Map<String, String> payload) {
        Article article = new Article(payload.get("title"), payload.get("abstract"), payload.get("body"), 2000);
        ArticleService.getInstance().addArticle(article);
    }

}
