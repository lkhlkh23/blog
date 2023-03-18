package bank.blog.service.search;

import bank.blog.domain.search.SearchDocument;

import java.util.List;

public interface GetSearchService {

    List<SearchDocument> search(final SearchCommand command);

}
