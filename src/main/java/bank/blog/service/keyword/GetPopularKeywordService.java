package bank.blog.service.keyword;

import bank.blog.domain.keyword.PopularKeyword;

import java.util.List;

public interface GetPopularKeywordService {

    List<PopularKeyword> getPopularKeywords(final int limit);

}
