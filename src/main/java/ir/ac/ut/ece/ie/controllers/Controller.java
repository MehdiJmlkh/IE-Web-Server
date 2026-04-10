package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Controller {
    public static void handleAction(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String command = httpRequest.getPath();
        var payload = httpRequest.getRequestBody();
        if (command.equals("filter-articles")) {
            var request = new FilterArticlesRequest(payload.get("search"));
            ArticleController.filterArticles(request, httpResponse);
        }
        else {
            var request = new AddArticleRequest(payload.get("title"), payload.get("abstract"), payload.get("body"));
            ArticleController.addArticle(request, httpResponse);
        }
    }

    public static void handle(HttpRequest httpRequest, HttpResponse httpResponse) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String path = httpRequest.getPath();

        if (httpRequest.isAction())
            Controller.handleAction(httpRequest, httpResponse);
        else if (httpRequest.isStaticResource())
            httpResponse.writeFile(path);
        else{
            httpResponse.writePage(path, httpRequest.getRequestParams());
        }
    }
}
