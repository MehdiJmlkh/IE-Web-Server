package ir.ac.ut.ece.ie.entities;

import java.util.ArrayList;
import java.util.List;

public class Article implements Comparable<Article> {
    private Integer id;
    private final String title;
    private final String articleAbstract;
    private final String body;
    private final int year;
    private List<Integer> citationIds = new ArrayList<>();


    public Article(String title, String articleAbstract, String body, int year) {
        this.title = title;
        this.articleAbstract = articleAbstract;
        this.body = body;
        this.year = year;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<Integer> getCitationIds() {
        return citationIds;
    }

    public void setCitationIds(List<Integer> citationIds) {
        this.citationIds = citationIds;
    }
}
