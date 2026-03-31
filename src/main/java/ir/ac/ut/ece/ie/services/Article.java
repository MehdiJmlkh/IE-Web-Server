package ir.ac.ut.ece.ie.services;

public class Article {
    private String title;
    private String articleAbstract;
    private String body;

    public Article(String title, String articleAbstract, String body) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public String getAbstract() {
        return articleAbstract;
    }

    public String getBody() {
        return body;
    }
}
