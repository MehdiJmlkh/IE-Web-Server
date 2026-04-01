package ir.ac.ut.ece.ie.pages;
import java.util.Map;

import ir.ac.ut.ece.ie.services.ArticleService;
import ir.ac.ut.ece.ie.services.Article;
import java.util.List;

public class ShowArticles {

    public byte[] pageBody(Map<String, String> params) {

        List<Article> articles = ArticleService.getInstance().getFilteredArticles();

        StringBuilder html = new StringBuilder();

        html.append("<html>");
        html.append("<head>");
        html.append("<title>Articles</title>");
        html.append("<link rel=\"stylesheet\" href=\"css/normalize.css\" />");
        html.append("<link rel=\"stylesheet\" href=\"bootstrap/bootstrap.min.css\" />");
        html.append("<link rel=\"stylesheet\" href=\"css/showArticles.css\" />");
        html.append("</head>");

        html.append("<body>");

        html.append("<form action=\"filter-articles\" method=\"post\" class=\"search-box\">");
        html.append("<input name=\"search\" class=\"search-box__input\" type=\"text\" placeholder=\"Search papers...\">");
        html.append("</form>");

        html.append("<h1>Articles</h1>");

        for (Article a : articles) {
            html.append("<div class=\"article\">");
            html.append("<a href=\"ArticleDetails?title=" + a.getTitle().toLowerCase().replaceAll(" ", "-") + "\" class=\"article__header\">")
                    .append(a.getTitle()).append("</a>");
            html.append("<p class=\"article__abstract\">").append(a.getAbstract(), 0, 400).append("...</p>");
            html.append("</div>");
        }

        html.append("</body>");
        html.append("</html>");

        return html.toString().getBytes();
    }
}

