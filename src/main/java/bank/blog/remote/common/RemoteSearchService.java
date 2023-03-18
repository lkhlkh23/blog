package bank.blog.remote.common;

import bank.blog.domain.search.SearchDocument;

import java.util.List;

public interface RemoteSearchService {

    List<SearchDocument> search(final String query, final String sort, final int page, final int size);

}
