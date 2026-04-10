package ir.ac.ut.ece.ie.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Article implements Comparable<Article> {
    private Integer id;
    private String title;

    @JsonProperty("abs")
    private String abs;
    private String body;
    private int year;
    private List<Integer> citationIds = new ArrayList<>();

    public Article() {}

    public Article(String title, String abs, String body, int year) {
        this.title = title;
        this.abs = abs;
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
        return abs;
    }

    public String getBody() {
        return body;
    }

    public int getYear() {
        return year;
    }

    @Override
    public int compareTo(Article other) {
        if (citationIds.size() == other.getCitationIds().size()) {
            return year - other.getYear();
        }
        return citationIds.size() - other.getCitationIds().size();
    }

    public List<Integer> getCitationIds() {
        return citationIds;
    }

    public void setCitationIds(List<Integer> citationIds) {
        this.citationIds = citationIds;
    }

    public String getSlug() {
         return title.toLowerCase().replaceAll(" ", "-");
    }
}
