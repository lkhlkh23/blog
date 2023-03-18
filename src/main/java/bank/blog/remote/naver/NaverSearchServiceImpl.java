package bank.blog.remote.naver;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.naver.dto.NaverSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "naverSearchService")
@RequiredArgsConstructor
public class NaverSearchServiceImpl implements RemoteSearchService {

    private final NaverClient naverClient;

    @Override
    public List<SearchDocument> search(final String query, final String sort, final int page, final int size) {
        final NaverSearchResponse response = naverClient.search(query, sort, page, size);
        if(response == null || response.getItems() == null || response.getItems().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return response.getItems()
                       .stream()
                       .map(NaverItemMapper.INSTANCE::from)
                       .collect(Collectors.toList());
    }

}
