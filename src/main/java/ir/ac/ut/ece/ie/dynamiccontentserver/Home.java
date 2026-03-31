package ir.ac.ut.ece.ie.dynamiccontentserver;

import java.io.IOException;
import java.io.InputStream;

public class Home {

    public byte[] pageBody() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("home.html")) {
            return is.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
