package bank.remote.common;

import bank.domain.search.SearchDocument;
import bank.service.search.SearchCommand;

import java.util.List;

public interface RemoteSearchService {

    List<SearchDocument> search(final SearchCommand command);

}
