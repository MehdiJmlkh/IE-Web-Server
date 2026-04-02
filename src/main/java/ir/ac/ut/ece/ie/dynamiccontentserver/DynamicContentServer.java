package ir.ac.ut.ece.ie.dynamiccontentserver;

import ir.ac.ut.ece.ie.controllers.ArticleController;
import ir.ac.ut.ece.ie.controllers.Controller;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;

import static ir.ac.ut.ece.ie.utils.HttpRequestUtil.getParams;
import static ir.ac.ut.ece.ie.utils.HttpRequestUtil.getPath;
import static ir.ac.ut.ece.ie.utils.UrlUtil.isStaticResource;

public class DynamicContentServer {
	public void start() throws IOException {
		ServerSocket serverSocket = new ServerSocket(9092);

		Socket socket;
		while ((socket = serverSocket.accept()) != null) {
			HttpRequest httpRequest = new HttpRequest(socket);
			HttpResponse httpResponse = new HttpResponse(socket.getOutputStream());

			String requestHeader = httpRequest.getHeader();
			if (requestHeader == null)
				continue;
			String path = httpRequest.getPath();
			try {
				if (httpRequest.isStaticResource())
					httpResponse.writeFile(path);
				else if (httpRequest.isAction()) {
					Controller.handleAction(httpRequest, httpResponse);
				}
				else{
					httpResponse.writePage(path, getParams(requestHeader));
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

	public static void main(String[] args) throws IOException {
		DynamicContentServer dcs = new DynamicContentServer();
		dcs.start();
	}

}
