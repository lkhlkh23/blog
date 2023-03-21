package bank.service.search;

import bank.domain.search.SearchResponse;
import bank.domain.search.SearchResponseCreator;
import bank.remote.common.RemoteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GetSearchServiceImpl implements GetSearchService {

    private final SearchServiceSelector selector;

    @Override
    public SearchResponse search(final SearchCommand command) {
        for (final RemoteSearchService searchService : selector.getAllSearchServices()) {
            final SearchResponse response = searchService.search(command);
            if(response.getDocuments() == null || response.getDocuments().isEmpty()) {
                continue;
            }

            return response;
        }

        return SearchResponseCreator.getInstance()
                                    .createDefault(command);
    }

}
