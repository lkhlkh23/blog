package bank.remote.naver;

import bank.domain.search.SearchResponse;
import bank.domain.search.SearchResponseCreator;
import bank.remote.common.RemoteSearchService;
import bank.remote.naver.dto.NaverSearchResponse;
import bank.service.search.SearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(2)
@Service(value = "naverSearchService")
@RequiredArgsConstructor
public class NaverSearchServiceImpl implements RemoteSearchService {

    private final NaverClient naverClient;

    @Override
    public SearchResponse search(final SearchCommand command) {
        final NaverSearchResponse response = naverClient.search(command.getQuery(),
                                                                command.getSort().getNaverCode(),
                                                                command.getPage(),
                                                                command.getSize());
        if(response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return new SearchResponse();
        }

        return SearchResponseCreator.getInstance().from(response, command);
    }

}
