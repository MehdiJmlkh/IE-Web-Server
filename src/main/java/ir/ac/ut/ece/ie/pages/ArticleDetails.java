package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.services.Article;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.util.List;
import java.util.Map;

public class ArticleDetails {
    public byte[] pageBody(Map<String, String> params) {
        String title = params.get("title");

        List<Article> articles = ArticleService.getInstance().getArticles();

        Article article = articles.stream()
                .filter(a -> a.getTitle().toLowerCase().replaceAll(" ", "-").equals(title))
                .findFirst()
                .orElse(null);
        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head>");
        html.append("<title>Articles</title>");
        html.append("<link rel=\"stylesheet\" href=\"css/normalize.css\" />");
        html.append("<link rel=\"stylesheet\" href=\"bootstrap/bootstrap.min.css\" />");
        html.append("<link rel=\"stylesheet\" href=\"css/showArticles.css\" />");
        html.append("</head>");

        html.append("<body>");

        html.append("<form class=\"search-box\">");
        html.append("<input class=\"search-box__input\" type=\"text\" placeholder=\"Search papers...\">");
        html.append("</form>");

        html.append("<h1>Articles</h1>");


        html.append("<div class=\"article\">");
        html.append("<a href=\"ArticleDetails?title=" + article.getTitle().toLowerCase().replaceAll(" ", "-") + "\" class=\"article__header\">")
                .append(article.getTitle()).append("</a>");
        html.append("<p class=\"article__abstract\">").append(article.getAbstract(), 0, 400).append("...</p>");
        html.append("</div>");


        html.append("</body>");
        html.append("</html>");

        return html.toString().getBytes();
    }
}
