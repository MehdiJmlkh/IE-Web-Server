package ir.ac.ut.ece.ie.entities;

public class Article implements Comparable<Article> {
    private final String title;
    private final String articleAbstract;
    private final String body;
    private final int year;


    public Article(String title, String articleAbstract, String body, int year) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.body = body;
        this.year = year;
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

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Article article) {
        return year - article.getYear();
    }
}
