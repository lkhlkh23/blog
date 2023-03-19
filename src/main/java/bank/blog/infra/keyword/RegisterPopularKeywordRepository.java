package bank.blog.infra.keyword;

public interface RegisterPopularKeywordRepository {

    void processIfPresentOrNot(final String query);

}
