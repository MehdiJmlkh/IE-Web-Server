package ir.ac.ut.ece.ie.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestUtil {
    public static String getPath(String httpRequestHeader) {
        StringTokenizer tokenizer = new StringTokenizer(httpRequestHeader, " ?");
        tokenizer.nextToken();
        return tokenizer.nextToken().substring(1);
    }

    public static Map<String, String> getParams(String httpRequestHeader) {
        Map<String, String> params = new HashMap<>();

        StringTokenizer tokenizer = new StringTokenizer(httpRequestHeader, " ");
        tokenizer.nextToken();

        String path = tokenizer.nextToken();

        int queryIndex = path.indexOf('?');
        if (queryIndex == -1) {
            return params;
        }

        String query = path.substring(queryIndex + 1);
        String[] pairs = query.split("&");

        for (String pair : pairs) {
            String[] keyValue = pair.split("=", 2);
            if (keyValue.length == 2) {
                params.put(keyValue[0], keyValue[1]);
            }
        }

        return params;
    }
}
