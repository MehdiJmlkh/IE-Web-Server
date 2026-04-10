package ir.ac.ut.ece.ie.http;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.UrlUtil.getFileExtension;

public class HttpResponse {
    private List<byte[]> response = new ArrayList<>();

    public HttpResponse() {
    }

    public static HttpResponse noContent() throws IOException {
        var httpResponse = new HttpResponse();

        String header = "HTTP/1.1 204 No Content\nConnection: close\n";
        httpResponse.response.add(header.getBytes());

        return httpResponse;
    }

    public static HttpResponse dynamicContent(String pageName, Map<String, String> params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        var httpResponse = new HttpResponse();

        Class<?> c = Class.forName("ir.ac.ut.ece.ie.pages." + pageName);
        Object page = c.getDeclaredConstructor().newInstance();
        Method method = c.getMethod("pageBody", Map.class);
        byte[] data = (byte[]) method.invoke(page, params);

        String header = createHeader((long) data.length, "html");

        httpResponse.response.add(header.getBytes());
        httpResponse.response.add(data);

        return httpResponse;
    }

    public static HttpResponse staticContent(String fileName) throws IOException {
        var httpResponse = new HttpResponse();

        File file = new File("./src/main/resources/" + fileName);

        String header = createHeader(file.length(), getFileExtension(fileName));

        try (FileInputStream fis = new FileInputStream(file);
             ByteArrayOutputStream buffer = new ByteArrayOutputStream()) {

            buffer.write(header.getBytes());

            byte[] data = new byte[1024];
            int size;

            while ((size = fis.read(data)) != -1) {
                buffer.write(data, 0, size);
            }

            httpResponse.response.add(buffer.toByteArray());
        }
        return httpResponse;
    }

    public byte[] getResponse() throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        for (byte[] arr : response) {
            out.write(arr);
        }

        return out.toByteArray();
    }

    private static String createHeader(Long contentLength, String textType) {
        return "HTTP1.1 200 OK \r\nContent-Type: text/" + textType + "\r\nContent.Length: "
                + contentLength
                + "\r\n\r\n";
    }
}
