package ir.ac.ut.ece.ie.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final BufferedReader reader;

    public HttpRequest(Socket socket) throws IOException {
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    public String readHeader() throws IOException {
        return reader.readLine();
    }

    public Map<String, String> readPayload() throws IOException {
        return parsePayload(getRawPayload());
    }

    private String getRawPayload() throws IOException {
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
            String value = keyValue.length > 1
                    ? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8)
                    : "";

            result.put(key, value);
        }
        return result;
    }
}
