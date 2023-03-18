package bank.blog.infra.keyword;

import bank.blog.domain.keyword.PopularKeyword;

import java.util.List;

public interface GetPopularKeywordRepository {

    List<PopularKeyword> getPopularKeywords();

}
