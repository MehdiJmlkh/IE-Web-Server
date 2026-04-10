package ir.ac.ut.ece.ie.controllers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;
import ir.ac.ut.ece.ie.http.HttpMethod;
import ir.ac.ut.ece.ie.http.HttpRequest;
import ir.ac.ut.ece.ie.http.HttpResponse;
import ir.ac.ut.ece.ie.mappers.ArticleMapper;
import ir.ac.ut.ece.ie.utils.UrlUtil;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;


public class Controller {
    private final ArticleController articleController = new ArticleController();
    private final ArticleMapper articleMapper = new ArticleMapper();

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
            FilterArticlesRequest request = articleMapper.toFilterArticleRequest(payload);
            return articleController.filterArticles(request);
        }
        else {
            AddArticleRequest request = articleMapper.toAddArticleRequest(payload);
            return articleController.addArticle(request);
        }
    }

    private HttpResponse handleGet(HttpRequest request) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        String path = request.getPath();
        if (path.isEmpty()) {
            return HttpResponse.ok().dynamicContent("ShowArticles", request.getRequestParams());
        }

        if (UrlUtil.isStaticResource(path)) {
            return HttpResponse.ok().staticContent(path);
        }
        else{
            return HttpResponse.ok().dynamicContent(path, request.getRequestParams());
        }
    }
}
