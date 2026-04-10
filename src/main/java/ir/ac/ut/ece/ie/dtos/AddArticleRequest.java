package ir.ac.ut.ece.ie.dtos;

public class AddArticleRequest {
    private String title;
    private String abs;
    private String body;

    public AddArticleRequest(String title, String abs, String body) {
        this.title = title;
        this.abs = abs;
        this.body = body;
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
}
