package bank.remote.kakao;

import bank.domain.search.SearchResponse;
import bank.domain.search.SearchResponseCreator;
import bank.remote.common.RemoteSearchService;
import bank.remote.kakao.dto.KakaoSearchResponse;
import bank.service.search.SearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Service(value = "kakaoSearchService")
@RequiredArgsConstructor
public class KakaoSearchServiceImpl implements RemoteSearchService {

    private final KakaoClient kakaoClient;

    @Override
    public SearchResponse search(final SearchCommand command) {
        final KakaoSearchResponse response = kakaoClient.search(command.getQuery(),
                                                                command.getSort().getKakoCode(),
                                                                command.getPage(),
                                                                command.getSize());
        if(response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            return new SearchResponse();
        }

        return SearchResponseCreator.getInstance().from(response, command);
    }

}
