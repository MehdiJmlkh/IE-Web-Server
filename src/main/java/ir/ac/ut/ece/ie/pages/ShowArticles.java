package ir.ac.ut.ece.ie.pages;
import java.util.Map;

import ir.ac.ut.ece.ie.services.ArticleService;
import ir.ac.ut.ece.ie.services.Article;
import java.util.List;

public class ShowArticles {

    public byte[] pageBody(Map<String, String> payload) {

        List<Article> articles = ArticleService.getInstance().getArticles();

        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head><title>Articles</title></head>");
        html.append("<body>");
        html.append("<h1>Articles</h1>");

        for (Article a : articles) {
            html.append("<div>");
            html.append("<h3>").append(a.getTitle()).append("</h3>");
            html.append("<p>").append(a.getAbstract()).append("</p>");
            html.append("</div>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString().getBytes();
    }
}

