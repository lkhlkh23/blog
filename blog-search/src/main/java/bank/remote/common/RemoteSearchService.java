package bank.remote.common;

import bank.domain.search.SearchResponse;
import bank.service.search.SearchCommand;

public interface RemoteSearchService {

    SearchResponse search(final SearchCommand command);

}
