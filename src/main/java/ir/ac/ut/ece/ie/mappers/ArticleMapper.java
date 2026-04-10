package ir.ac.ut.ece.ie.mappers;

import ir.ac.ut.ece.ie.dtos.AddArticleRequest;
import ir.ac.ut.ece.ie.dtos.FilterArticlesRequest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ArticleMapper {
    public AddArticleRequest toAddArticleRequest(Map<String, String> requestBody) {
        List<Integer> citations = requestBody.keySet().stream()
                .filter(s -> s.startsWith("citations[]"))
                .map(s-> s.split("=")[1])
                .map(Integer::valueOf)
                .collect(Collectors.toList());

        return new AddArticleRequest(
                requestBody.get("title"),
                Integer.valueOf(requestBody.get("year")),
                requestBody.get("abstract"),
                requestBody.get("body"),
                citations
        );
    }

    public FilterArticlesRequest toFilterArticleRequest(Map<String, String> requestBody) {
        return new FilterArticlesRequest(requestBody.get("search"));
    }
}
