package ir.ac.ut.ece.ie.repositories;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.ac.ut.ece.ie.entities.Article;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepository {

    private static final ArticleRepository instance = new ArticleRepository();
    private List<Article> articles = new ArrayList<>();
    private Integer lastGeneratedId = 0;
    private String searchInput = null;

    private ArticleRepository() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            mapper.readValue(
                    new File("./src/main/resources/data/sampleArticles.json"),
                    new TypeReference<List<Article>>() {}
            ).forEach(this::addArticle);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArticleRepository getInstance() {
        return instance;
    }

    public void addArticle(Article article) {
        article.setId(++lastGeneratedId);
        articles.add(article);
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Article> getFilteredArticles() {
        if (searchInput == null)
            return articles.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        return articles
                .stream()
                .filter(article ->
                        article.getTitle().toLowerCase().contains(searchInput) ||
                        article.getAbstract().toLowerCase().contains(searchInput))
                .sorted((a, b) -> {
                    boolean aHasSearchInput = a.getTitle().toLowerCase().contains(searchInput);
                    boolean bHasSearchInput = b.getTitle().toLowerCase().contains(searchInput);

                    if (aHasSearchInput && !bHasSearchInput) {
                        return -1;
                    }
                    else if (!aHasSearchInput && bHasSearchInput) {
                        return 1;
                    }
                    return b.compareTo(a);
                })
                .collect(Collectors.toList());
    }


    public void setFilter(String searchInput) {
        this.searchInput = searchInput.toLowerCase();
    }

    public Article findById(Integer id) {
        return articles.stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
