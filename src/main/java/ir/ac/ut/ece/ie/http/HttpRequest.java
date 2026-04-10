package ir.ac.ut.ece.ie.http;

import ir.ac.ut.ece.ie.utils.HttpRequestUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import ir.ac.ut.ece.ie.utils.UrlUtil;

public class HttpRequest {
    private final String header;
    private final Map<String, String> requestParams;
    Map<String, String> requestBody;

    public HttpRequest(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        header = reader.readLine();
        requestParams = HttpRequestUtil.getParams(header);
        requestBody = parsePayload(readRawPayload(reader));
        System.out.println(header);
    }

    public boolean isValid() {
        return header != null;
    }

    public HttpMethod getMethod() {
        String method = header.split(" ")[0].toLowerCase();
        if (method.equals("get")) {
            return HttpMethod.GET;
        } else if (method.equals("post")) {
            return HttpMethod.POST;
        }
        return null;
    }

    private String readRawPayload(BufferedReader reader) throws IOException {
        String line;
        int contentLength = 0;
        while (!(line = reader.readLine()).isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }

        char[] bodyChars = new char[contentLength];
        reader.read(bodyChars, 0, contentLength);

        return new String(bodyChars);
    }

    private Map<String, String> parsePayload(String payload) {
        if (payload.isEmpty())
            return new HashMap<>();

        String[] pairs = payload.split("&");
        Map<String, String> result = new HashMap<>();

        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
            String value = keyValue.length > 1 ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8) : "";

            result.put(key, value);
        }
        return result;
    }

    public String getPath() {
        return HttpRequestUtil.getPath(header);
    }

    public boolean isStaticResource() {
        return UrlUtil.isStaticResource(getPath());
    }

    public Map<String, String> getRequestBody() {
        return requestBody;
    }

    public Map<String, String> getRequestParams() {return requestParams;}
}
