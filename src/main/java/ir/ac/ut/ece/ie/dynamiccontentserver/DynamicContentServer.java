package ir.ac.ut.ece.ie.dynamiccontentserver;

import ir.ac.ut.ece.ie.controllers.Controller;
import ir.ac.ut.ece.ie.http.HttpRequest;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

public class DynamicContentServer {
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9092);

		Socket socket;
		while ((socket = serverSocket.accept()) != null) {
			HttpRequest httpRequest = new HttpRequest(socket);

			if (!httpRequest.isValid())
				continue;
			try {
				var httpResponse = Controller.handle(httpRequest);
				socket.getOutputStream().write(httpResponse.getResponse());

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

	public static void main(String[] args) throws IOException {
		DynamicContentServer dcs = new DynamicContentServer();
		dcs.start();
	}

}
