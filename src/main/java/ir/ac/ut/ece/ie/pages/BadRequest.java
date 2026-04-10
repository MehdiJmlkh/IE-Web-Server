package ir.ac.ut.ece.ie.pages;

import ir.ac.ut.ece.ie.entities.Article;
import ir.ac.ut.ece.ie.repositories.ArticleRepository;

import java.util.List;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class BadRequest {
    public byte[] pageBody(Map<String, String> params) {
        String html = loadTemplate("badRequest.html")
                .replace("{error}", params.get("error"));
        return html.getBytes();
    }
}
