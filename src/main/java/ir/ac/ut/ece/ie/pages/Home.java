package ir.ac.ut.ece.ie.pages;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class Home {

    public byte[] pageBody(Map<String, String> payload) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("addArticle.html")) {
            return is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
