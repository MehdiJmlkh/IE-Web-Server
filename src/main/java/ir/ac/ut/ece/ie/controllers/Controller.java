package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.services.ArticleService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Controller {
    public static void handleAction(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String command = httpRequest.getPath();
        if (command.equals("filter-articles")) {
            ArticleController.filterArticles(httpRequest.getPayload(), httpResponse);
        }
        else {
            ArticleController.addArticle(httpRequest.getPayload(), httpResponse);
        }
    }
}
