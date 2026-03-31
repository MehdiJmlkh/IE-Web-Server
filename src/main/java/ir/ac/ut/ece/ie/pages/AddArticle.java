package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.services.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class AddArticle {
    public byte[] pageBody(Map<String, String> payload) {
        System.out.println(payload);
        ArticleService.getInstance().addArticle(new Article(payload.get("title"), payload.get("abstract"), ""));

        System.out.println(ArticleService.getInstance().getArticles());

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("addArticle.html")) {
            return is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
