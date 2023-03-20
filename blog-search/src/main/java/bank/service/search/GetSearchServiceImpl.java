package bank.service.search;

import bank.domain.search.SearchDocument;
import bank.remote.common.RemoteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GetSearchServiceImpl implements GetSearchService {

    private final SearchServiceSelector selector;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        for (final RemoteSearchService searchService : selector.getAllSearchServices()) {
            final List<SearchDocument> response = searchService.search(command);
            if(response.isEmpty()) {
                continue;
            }

            return response;
        }

        return Collections.EMPTY_LIST;
    }

}
