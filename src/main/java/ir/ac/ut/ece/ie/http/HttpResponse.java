package ir.ac.ut.ece.ie.http;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

    public void writePage(String pageName) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        Class<?> c = Class.forName("ir.ac.ut.ece.ie.pages." + pageName);
        Object page = c.getDeclaredConstructor().newInstance();
        Method method = c.getMethod("pageBody");
        byte[] data = (byte[]) method.invoke(page);

        String header = createHeader((long) data.length, "html");

        outputStream.write(header.getBytes());
        outputStream.write(data);
    }

    public void writeFile(String fileName) throws IOException {
        File file = new File("./src/main/resources/" + fileName);
        String header = createHeader(file.length(), getFileExtension(fileName));
        outputStream.write(header.getBytes());

        RandomAccessFile raf = new RandomAccessFile(file, "r");
        byte[] data = new byte[1024];
        int size = 0;
        try {
            while((size = raf.read(data)) != -1) {
                outputStream.write(data, 0 , size);
            }
        } catch(IOException e) {
            raf.close();
        }
    }
}
