package ir.ac.ut.ece.ie.dynamiccontentserver;

import ir.ac.ut.ece.ie.controllers.Controller;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicContentServer {
	private final Controller controller = new Controller();

	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(8080);

		System.out.println("Server started on port 8080");

		Socket socket;
		while ((socket = serverSocket.accept()) != null) {
			HttpRequest httpRequest = new HttpRequest(socket);

			if (!httpRequest.isValid()) {
				System.out.println("Invalid HTTP request");
				socket.close();
				continue;
			}
			try {
				HttpResponse httpResponse = controller.handle(httpRequest);
				socket.getOutputStream().write(httpResponse.getResponse());

			} catch (FileNotFoundException |
					 ClassNotFoundException |
					 InstantiationException |
					 IllegalAccessException |
					 IllegalArgumentException |
					 InvocationTargetException |
					 NoSuchMethodException |
					 SecurityException e) {

				System.out.println("[ERROR] " + e.getMessage());

				String header = "HTTP/1.1 404 Page Not Found\r\n\r\n";
				socket.getOutputStream().write(header.getBytes());
			}

			socket.getOutputStream().flush();
			socket.close();
		}
		serverSocket.close();
	}
}
