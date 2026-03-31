package ir.ac.ut.ece.ie.dynamiccontentserver;

import java.util.Date;
import java.util.Map;

public class UploadArticle {
    public byte[] pageBody(Map<String, String> payload) {
        System.out.println(payload);
        return ("<html>"
                + "<header>"
                + "<title>"
                + "Time Page"
                + "</title>"
                + "</header>"
                + "<body>"
                + "The current time is: "
                + (new Date()).toString()
                + "</body>").getBytes();
    }
}
