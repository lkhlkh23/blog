package bank.blog.remote.common;

import bank.blog.domain.search.SearchDocument;
import bank.blog.service.search.SearchCommand;

import java.util.List;

public interface RemoteSearchService {

    List<SearchDocument> search(final SearchCommand command);

}
