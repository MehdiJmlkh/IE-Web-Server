package ir.ac.ut.ece.ie.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
    private final String header;
    Map<String, String> payload;

    public HttpRequest(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        header = reader.readLine();
        payload = parsePayload(readRawPayload(reader));
    }

    public boolean isAction() {
        return !payload.isEmpty();
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

            String key = keyValue[0];
            String value = keyValue.length > 1 ? keyValue[1] : "";

            result.put(key, value);
        }
        return result;
    }

    public String getHeader() {
        return header;
    }

    public Map<String, String> getPayload() {
        return payload;
    }
}
