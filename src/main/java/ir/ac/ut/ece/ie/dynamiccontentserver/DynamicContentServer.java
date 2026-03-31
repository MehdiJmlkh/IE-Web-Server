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
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String readLine = reader.readLine();
			if (readLine == null)
				continue;
			String pageName = getPageName(readLine);
			try {

				if (!getFileExtension(pageName).equals("class")) {
				File file = new File("./src/main/resources/" + pageName);
				String header = "HTTP1.1 200 OK \r\nContent-Type: text/" + getFileExtension(pageName) + "\r\nContent.Length: "
						+ file.length()
						+ "\r\n\r\n";

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
			else {
					Class<?> c = Class.forName("ir.ac.ut.ece.ie.dynamiccontentserver." + pageName);
					Object page = c.getDeclaredConstructor().newInstance();
					Method method = c.getMethod("pageBody");
					byte[] data = (byte[]) method.invoke(page);

					String header = "HTTP1.1 200 OK \r\nContent-Type: text/html\r\nContent.Length: "
							+ data.length
							+ "\r\n\r\n";

					socket.getOutputStream().write(header.getBytes());
					socket.getOutputStream().write(data);
				}
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
