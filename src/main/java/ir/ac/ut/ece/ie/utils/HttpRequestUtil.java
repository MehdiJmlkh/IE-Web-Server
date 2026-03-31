package ir.ac.ut.ece.ie.utils;

import java.util.StringTokenizer;

public class HttpRequestUtil {
    public static String getRoute(String httpRequest) {
        StringTokenizer tokenizer = new StringTokenizer(httpRequest, " ");
        tokenizer.nextToken();
        return tokenizer.nextToken().substring(1);
    }

}
