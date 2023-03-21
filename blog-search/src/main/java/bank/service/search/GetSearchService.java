package bank.service.search;

import bank.domain.search.SearchResponse;

public interface GetSearchService {

    SearchResponse search(final SearchCommand command);

}
