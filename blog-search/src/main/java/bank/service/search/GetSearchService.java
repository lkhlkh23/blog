package bank.service.search;

import bank.domain.search.SearchDocument;

import java.util.List;

public interface GetSearchService {

    List<SearchDocument> search(final SearchCommand command);

}
