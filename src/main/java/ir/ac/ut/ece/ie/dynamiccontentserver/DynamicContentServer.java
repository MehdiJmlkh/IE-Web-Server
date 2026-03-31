package ir.ac.ut.ece.ie.dynamiccontentserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static ir.ac.ut.ece.ie.utils.HttpRequestUtil.getRoute;
import static ir.ac.ut.ece.ie.utils.UrlUtil.getFileExtension;
import static ir.ac.ut.ece.ie.utils.UrlUtil.isStaticResource;

public class DynamicContentServer {
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9092);

		Socket socket;
		while ((socket = serverSocket.accept()) != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readLine = readHeader(reader);
			if (readLine == null)
				continue;
			String body = readHttpPayload(reader);
			if (!body.isEmpty())
				System.out.println(body);
			System.out.println(parsePayload(body));
			String route = getRoute(readLine);
			try {
				if (isStaticResource(route))
					sendFile(route, socket);
				else
					sendPage(route, socket);

			} catch (FileNotFoundException |
					ClassNotFoundException | InstantiationException | 
					IllegalAccessException | IllegalArgumentException | 
					InvocationTargetException | NoSuchMethodException | 
					SecurityException e) {
				String header = "HTTP1.1 404 Page Not Found\r\n\r\n";
				socket.getOutputStream().write(header.getBytes());
			}

			socket.getOutputStream().flush();
			socket.close();
		}
		serverSocket.close();
	}

	private static String readHeader(BufferedReader reader) throws IOException {
		return reader.readLine();
	}

	private static String readHttpPayload(BufferedReader reader) throws IOException {
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
		String[] pairs = payload.split("&");
		Map<String, String> result = new HashMap<>();

		for (String pair : pairs) {
			String[] keyValue = pair.split("=", 2);

			String key = URLDecoder.decode(keyValue[0], StandardCharsets.UTF_8);
			String value = keyValue.length > 1
					? URLDecoder.decode(keyValue[1], StandardCharsets.UTF_8)
					: "";

			result.put(key, value);
		}
		return result;
	}
	private static String getHeader(Long contentLength, String textType) {
        return "HTTP1.1 200 OK \r\nContent-Type: text/" + textType + "\r\nContent.Length: "
                + contentLength
                + "\r\n\r\n";
	}

	private static void sendPage(String pageName, Socket socket) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
		Class<?> c = Class.forName("ir.ac.ut.ece.ie.dynamiccontentserver." + pageName);
		Object page = c.getDeclaredConstructor().newInstance();
		Method method = c.getMethod("pageBody");
		byte[] data = (byte[]) method.invoke(page);

		String header = getHeader((long) data.length, "html");

		socket.getOutputStream().write(header.getBytes());
		socket.getOutputStream().write(data);
	}

	private void sendFile(String fileName, Socket socket) throws IOException {
		File file = new File("./src/main/resources/" + fileName);
		String header = getHeader(file.length(), getFileExtension(fileName));

		RandomAccessFile raf = new RandomAccessFile(file, "r");
		byte[] data = new byte[1024];
		int size = 0;
		socket.getOutputStream().write(header.getBytes());
		try {
			while((size = raf.read(data)) != -1) {
				socket.getOutputStream().write(data, 0 , size);
			}
		} catch(IOException e) {
			raf.close();
		}
	}

	public static void main(String[] args) throws IOException {
		DynamicContentServer dcs = new DynamicContentServer();
		dcs.start();
	}

}
