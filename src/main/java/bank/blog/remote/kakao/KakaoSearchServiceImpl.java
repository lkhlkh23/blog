package bank.blog.remote.kakao;

import bank.blog.domain.search.SearchDocument;
import bank.blog.domain.search.SortType;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.kakao.dto.KakaoSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service(value = "kakaoSearchService")
@RequiredArgsConstructor
public class KakaoSearchServiceImpl implements RemoteSearchService {

    private final KakaoClient kakaoClient;

    @Override
    public List<SearchDocument> search(final String query, final SortType sort, final int page, final int size) {
        final KakaoSearchResponse response = kakaoClient.search(query, sort.getKakoCode(), page, size);
        if(response == null || response.getDocuments() == null || response.getDocuments().isEmpty()) {
            return Collections.EMPTY_LIST;
        }

        return response.getDocuments()
                       .stream()
                       .map(KakaoDocumentMapper.INSTANCE::from)
                       .collect(Collectors.toList());
    }

}
