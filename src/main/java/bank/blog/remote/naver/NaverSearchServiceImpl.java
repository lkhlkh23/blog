package bank.blog.remote.naver;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.naver.dto.NaverSearchResponse;
import bank.blog.service.search.SearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(2)
@Service(value = "naverSearchService")
@RequiredArgsConstructor
public class NaverSearchServiceImpl implements RemoteSearchService {

    private final NaverClient naverClient;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        final NaverSearchResponse response = naverClient.search(command.getQuery(),
                                                                command.getSort().getNaverCode(),
                                                                command.getPage(),
                                                                command.getSize());
        if(response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return response.getItems()
                       .stream()
                       .map(NaverItemMapper.INSTANCE::from)
                       .collect(Collectors.toList());
    }

}
