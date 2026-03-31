package ir.ac.ut.ece.ie.dynamiccontentserver;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.StringTokenizer;

public class DynamicContentServer {
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9092);

		Socket socket;
		while ((socket = serverSocket.accept()) != null) {
			String readLine = readLine(socket);
			if (readLine == null)
				continue;

			String pageName = getPageName(readLine);
			try {
				if (getFileExtension(pageName).equals("class"))
					sendClass(pageName, socket);
				else
					sendFile(pageName, socket);
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

	private static String readLine(Socket socket) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        return reader.readLine();
	}

	private static void sendClass(String pageName, Socket socket) throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
		Class<?> c = Class.forName("ir.ac.ut.ece.ie.dynamiccontentserver." + pageName);
		Object page = c.getDeclaredConstructor().newInstance();
		Method method = c.getMethod("pageBody");
		byte[] data = (byte[]) method.invoke(page);

		String header = getHeader((long) data.length, "html");

		socket.getOutputStream().write(header.getBytes());
		socket.getOutputStream().write(data);
	}

	private static String getHeader(Long contentLength, String textType) {
        return "HTTP1.1 200 OK \r\nContent-Type: text/" + textType + "\r\nContent.Length: "
                + contentLength
                + "\r\n\r\n";
	}

	private void sendFile(String pageName, Socket socket) throws IOException {
		File file = new File("./src/main/resources/" + pageName);
		String header = getHeader(file.length(), getFileExtension(pageName));

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

	private String getPageName(String readLine) {
		StringTokenizer tokenizer = new StringTokenizer(readLine, " ");
		tokenizer.nextToken();
		String fileName = tokenizer.nextToken().substring(1);
		return fileName;
	}

	private String getFileExtension(String pageName) {
		String[] parts = pageName.split("\\.");
		if (parts.length <= 1) {
			return "class";
		}
		String fileExtension = parts[parts.length - 1];
		return fileExtension;
	}

	public static void main(String[] args) throws IOException {
		DynamicContentServer dcs = new DynamicContentServer();
		dcs.start();
	}

}
