package ir.ac.ut.ece.ie.pages;

import java.util.Map;

import static ir.ac.ut.ece.ie.utils.FileUtil.loadTemplate;

public class AddArticle {
    public byte[] pageBody(Map<String, String> params) {
        String html = loadTemplate("addArticle.html");
        return html.getBytes();
    }
}
