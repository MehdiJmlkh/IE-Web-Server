package ir.ac.ut.ece.ie.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class HttpRequestUtil {
    public static String getRoute(String httpRequest) {
        StringTokenizer tokenizer = new StringTokenizer(httpRequest, " ?");
        tokenizer.nextToken();
        return tokenizer.nextToken().substring(1);
    }

    public static Map<String, String> getParams(String httpRequest) {
        Map<String, String> params = new HashMap<>();

        StringTokenizer tokenizer = new StringTokenizer(httpRequest, " ");
        tokenizer.nextToken(); // skip GET/POST

        String path = tokenizer.nextToken(); // /path?name=ali&age=20

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
