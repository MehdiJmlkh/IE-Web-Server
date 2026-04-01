package ir.ac.ut.ece.ie.utils;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtil {
    public static String loadTemplate(String name) {
        try {
            URL url = FileUtil.class.getClassLoader().getResource(name);
            if (url == null) return "";

            Path path = Paths.get(url.toURI());
            return Files.readString(path, StandardCharsets.UTF_8);

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
