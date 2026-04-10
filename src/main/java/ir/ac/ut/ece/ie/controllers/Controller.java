package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpMethod;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Controller {
    private final ArticleController articleController = new ArticleController();

    public HttpResponse handle(HttpRequest httpRequest) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        var method = httpRequest.getMethod();
        if (method == HttpMethod.POST)
            return handlePost(httpRequest);
        else if (method == HttpMethod.GET) {
            return handleGet(httpRequest);
        }
        return null;
    }

    private HttpResponse handlePost(HttpRequest httpRequest) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String command = httpRequest.getPath();
        var payload = httpRequest.getRequestBody();
        if (command.equals("filter-articles")) {
            var request = new FilterArticlesRequest(payload.get("search"));
            return articleController.filterArticles(request);
        }
        else {
            var request = new AddArticleRequest(payload.get("title"), payload.get("abstract"), payload.get("body"));
            return articleController.addArticle(request);
        }
    }

    private HttpResponse handleGet(HttpRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String path = request.getPath();

        if (request.isStaticResource()) {
            return HttpResponse.writeFile(path);
        }
        else{
            return HttpResponse.writePage(path, request.getRequestParams());
        }
    }
}
