package ir.ac.ut.ece.ie.dtos;

public class FilterArticlesRequest {
    private final String searchInput;

    public FilterArticlesRequest(String searchInput) {
        this.searchInput = searchInput;
    }

    public String getSearchInput() {
        return searchInput;
    }
}
