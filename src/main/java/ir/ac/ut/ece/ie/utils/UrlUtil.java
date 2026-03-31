package ir.ac.ut.ece.ie.utils;

public class UrlUtil {
    public static boolean isStaticResource(String route) {
        return route.contains(".");
    }

    public static String getFileExtension(String fileName) {
        String[] parts = fileName.split("\\.");
        if (parts.length <= 1) {
            return null;
        }
        return parts[parts.length - 1];
    }
}
