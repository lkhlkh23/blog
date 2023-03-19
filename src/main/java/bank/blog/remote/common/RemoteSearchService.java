package bank.blog.remote.common;

import bank.blog.domain.search.SearchDocument;
import bank.blog.domain.search.SortType;

import java.util.List;

public interface RemoteSearchService {

    List<SearchDocument> search(final String query, final SortType sort, final int page, final int size);

}
