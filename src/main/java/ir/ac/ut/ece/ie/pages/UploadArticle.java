package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.services.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.util.Date;
import java.util.Map;

public class UploadArticle {
    public byte[] pageBody(Map<String, String> payload) {
        System.out.println(payload);
        ArticleService.getInstance().addArticle(new Article(payload.get("title"), payload.get("abstract"), ""));

        System.out.println(ArticleService.getInstance().getArticles());
        return ("<html>"
                + "<header>"
                + "<title>"
                + "Time Page"
                + "</title>"
                + "</header>"
                + "<body>"
                + "The current time is: "
                + (new Date()).toString()
                + "</body>").getBytes();
    }
}
