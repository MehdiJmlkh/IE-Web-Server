package ir.ac.ut.ece.ie.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.UrlUtil.getFileExtension;

public class HttpResponse {
    private final OutputStream outputStream;

    public HttpResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    private static String getHeader(Long contentLength, String textType) {
        return "HTTP1.1 200 OK \r\nContent-Type: text/" + textType + "\r\nContent.Length: "
                + contentLength
                + "\r\n\r\n";
    }

    public void sendPage(String pageName, Map<String, String> payload) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        Class<?> c = Class.forName("ir.ac.ut.ece.ie.dynamiccontentserver." + pageName);
        Object page = c.getDeclaredConstructor().newInstance();
        Method method = c.getMethod("pageBody", Map.class);
        byte[] data = (byte[]) method.invoke(page, payload);

        String header = getHeader((long) data.length, "html");

        outputStream.write(header.getBytes());
        outputStream.write(data);
    }

    public void sendFile(String fileName) throws IOException {
        File file = new File("./src/main/resources/" + fileName);
        String header = getHeader(file.length(), getFileExtension(fileName));

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        byte[] data = new byte[1024];
        int size = 0;
        outputStream.write(header.getBytes());
        try {
            while((size = raf.read(data)) != -1) {
                outputStream.write(data, 0 , size);
            }
        } catch(IOException e) {
            raf.close();
        }
    }
}
