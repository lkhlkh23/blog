package bank.blog.remote.kakao;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.kakao.dto.KakaoSearchResponse;
import bank.blog.service.search.SearchCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Order(1)
@Service(value = "kakaoSearchService")
@RequiredArgsConstructor
public class KakaoSearchServiceImpl implements RemoteSearchService {

    private final KakaoClient kakaoClient;

    @Override
    public List<SearchDocument> search(final SearchCommand command) {
        final KakaoSearchResponse response = kakaoClient.search(command.getQuery(),
                                                                command.getSort().getKakoCode(),
                                                                command.getPage(),
                                                                command.getSize());
        if(response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return response.getDocuments()
                       .stream()
                       .map(KakaoDocumentMapper.INSTANCE::from)
                       .collect(Collectors.toList());
    }

}
