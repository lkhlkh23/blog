package bank.service.keyword;

import bank.domain.keyword.PopularKeyword;

import java.util.List;

public interface GetPopularKeywordService {

    List<PopularKeyword> getPopularKeywords(final int limit);

}
