package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpMethod;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.utils.UrlUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;


public class Controller {
    private final ArticleController articleController = new ArticleController();

    public HttpResponse handle(HttpRequest httpRequest) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        HttpMethod method = httpRequest.getMethod();
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
            List<Integer> citations = payload.keySet().stream()
                    .filter(s -> s.startsWith("citations[]"))
                    .map(s-> s.split("=")[1])
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            var request = new AddArticleRequest(payload.get("title"), payload.get("abstract"), payload.get("body"), citations);
            return articleController.addArticle(request);
        }
    }

    private HttpResponse handleGet(HttpRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String path = request.getPath();

        if (UrlUtil.isStaticResource(path)) {
            return HttpResponse.ok().staticContent(path);
        }
        else{
            return HttpResponse.ok().dynamicContent(path, request.getRequestParams());
        }
    }
}
