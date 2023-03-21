package bank.domain.search;

import bank.remote.kakao.KakaoDocumentMapper;
import bank.remote.kakao.dto.KakaoSearchResponse;
import bank.remote.naver.NaverItemMapper;
import bank.remote.naver.dto.NaverSearchResponse;
import bank.service.search.SearchCommand;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchResponseCreator {

    private static final SearchResponseCreator INSTANCE = new SearchResponseCreator();

    public static SearchResponseCreator getInstance() {
        return INSTANCE;
    }

    public SearchResponse from(final KakaoSearchResponse response, final SearchCommand command) {
        final SearchResponse.SearchPage page =  SearchResponse.SearchPage.builder()
                                                                         .total(response.getMeta().getTotalCount())
                                                                         .requestPage(command.getPage())
                                                                         .requestSize(command.getSize())
                                                                         .isLast(response.getMeta().getIsEnd())
                                                                         .build();
        final List<SearchDocument> documents = response.getDocuments()
                                                       .stream()
                                                       .map(KakaoDocumentMapper.INSTANCE::from)
                                                       .collect(Collectors.toList());
        return new SearchResponse(page, documents);
    }

    public SearchResponse from(final NaverSearchResponse response, final SearchCommand command) {
        final SearchResponse.SearchPage page =  SearchResponse.SearchPage.builder()
                                                                         .total(response.getTotal())
                                                                         .requestPage(command.getPage())
                                                                         .requestSize(command.getSize())
                                                                         .isLast(response.getTotal() <= command.getPage() * command.getSize())
                                                                         .build();
        final List<SearchDocument> documents = response.getItems()
                                                       .stream()
                                                       .map(NaverItemMapper.INSTANCE::from)
                                                       .collect(Collectors.toList());
        return new SearchResponse(page, documents);
    }

    public SearchResponse createDefault(final SearchCommand command) {
        final SearchResponse.SearchPage page = SearchResponse.SearchPage.builder()
                                                                        .requestPage(command.getPage())
                                                                        .requestSize(command.getSize())
                                                                        .isLast(true)
                                                                        .build();
        return new SearchResponse(page, Collections.EMPTY_LIST);
    }

}
