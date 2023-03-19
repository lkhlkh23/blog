package bank.blog.service.search;

import bank.blog.domain.search.SearchDocument;
import bank.blog.exception.SearchResultNotFoundException;
import bank.blog.remote.common.RemoteSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GetSearchServiceImpl implements GetSearchService {

    private final SearchServiceSelector selector;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        for (final RemoteSearchService searchService : selector.getAllSearchServices()) {
            final List<SearchDocument> response = searchService.search(command.getQuery(),
                                                                       command.getSort(),
                                                                       command.getPage(),
                                                                       command.getSize());
            if(response.isEmpty()) {
                continue;
            }

            return response;
        }

        throw new SearchResultNotFoundException("No API search results");
    }

}
