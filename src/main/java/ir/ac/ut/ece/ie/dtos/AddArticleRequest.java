package ir.ac.ut.ece.ie.dtos;

import java.util.List;

public class AddArticleRequest {
    private String title;
    private String abs;
    private String body;
    private final List<Integer> citationIds;

    public AddArticleRequest(String title, String abs, String body, List<Integer> citationIds) {
        this.title = title;
        this.abs = abs;
        this.body = body;
        this.citationIds = citationIds;
    }

    public String getTitle() {
        return title;
    }

    public String getAbs() {
        return abs;
    }

    public String getBody() {
        return body;
    }

    public List<Integer> getCitationIds() {
        return citationIds;
    }
}
