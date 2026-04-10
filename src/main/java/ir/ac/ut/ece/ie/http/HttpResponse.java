package ir.ac.ut.ece.ie.http;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.UrlUtil.getFileExtension;

public class HttpResponse {
    private final OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private static String createHeader(Long contentLength, String textType) {
        return "HTTP1.1 200 OK \r\nContent-Type: text/" + textType + "\r\nContent.Length: "
                + contentLength
                + "\r\n\r\n";
    }

    public void sendNoContentResponse() throws IOException {
        String header = "HTTP/1.1 204 No Content\nConnection: close\n";
        outputStream.write(header.getBytes());
    }

    public void writePage(String pageName, Map<String, String> params) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        Class<?> c = Class.forName("ir.ac.ut.ece.ie.pages." + pageName);
        Object page = c.getDeclaredConstructor().newInstance();
        Method method = c.getMethod("pageBody", Map.class);
        byte[] data = (byte[]) method.invoke(page, params);

        String header = createHeader((long) data.length, "html");

        outputStream.write(header.getBytes());
        outputStream.write(data);
    }

    public void writeFile(String fileName) throws IOException {
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

            outputStream.write(buffer.toByteArray());
        }
    }
}
