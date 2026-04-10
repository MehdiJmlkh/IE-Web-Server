package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpMethod;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Controller {
    public HttpResponse handleAction(HttpRequest httpRequest) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String command = httpRequest.getPath();
        var payload = httpRequest.getRequestBody();
        if (command.equals("filter-articles")) {
            var request = new FilterArticlesRequest(payload.get("search"));
            return ArticleController.filterArticles(request);
        }
        else {
            var request = new AddArticleRequest(payload.get("title"), payload.get("abstract"), payload.get("body"));
            return ArticleController.addArticle(request);
        }
    }

    public HttpResponse handle(HttpRequest httpRequest) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String path = httpRequest.getPath();

        var method = httpRequest.getMethod();
        if (method == HttpMethod.POST)
            return handleAction(httpRequest);
        else if (httpRequest.isStaticResource()) {
            return HttpResponse.writeFile(path);
        }
        else{
            return HttpResponse.writePage(path, httpRequest.getRequestParams());
        }
    }
}
