package ir.ac.ut.ece.ie.dynamiccontentserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Home {

    public byte[] pageBody() {
        try {
            return Files.readAllBytes(Path.of("./src/main/resources/home.html"));
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
