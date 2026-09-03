package ir.ac.ut.ece.ie.utils;

import java.nio.charset.StandardCharsets;
import java.io.InputStream;

public class FileUtil {

    public static String loadTemplate(String name) {
        try (InputStream inputStream =
                     FileUtil.class.getClassLoader().getResourceAsStream(name)) {

            if (inputStream == null) {
                return "";
            }

            return new String(
                    inputStream.readAllBytes(),
                    StandardCharsets.UTF_8
            );

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
