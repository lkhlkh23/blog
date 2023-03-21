package bank.service.search;

import bank.domain.search.SearchDocument;
import bank.domain.search.SearchResponse;
import bank.domain.search.SortType;
import bank.remote.kakao.KakaoSearchServiceImpl;
import bank.remote.naver.NaverSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {GetSearchServiceImpl.class, SearchServiceSelector.class})
class GetSearchServiceTest {

    @Autowired
    private GetSearchService sut;

    @MockBean
    private KakaoSearchServiceImpl kakaoSearchService;

    @MockBean
    private NaverSearchServiceImpl naverSearchService;

    private SearchCommand command;

    @BeforeEach
    void setUp() {
        command = SearchCommand.builder()
                               .query("테스트")
                               .page(1)
                               .sort(SortType.RECENCY)
                               .size(10)
                               .build();
    }

    @Test
    @DisplayName("카카오 검색결과가 있을 때, 카카오 결과 반환")
    void test_search_whenKakaoIsSuccessThenReturnKakaoResults() {
        // given
        final SearchResponse kakaoResponse = new SearchResponse(null, List.of(createSearchDocument("kakao")));
        final SearchResponse naverResponse = new SearchResponse(null, List.of(createSearchDocument("naver")));

        // when
        when(kakaoSearchService.search(command)).thenReturn(kakaoResponse);
        when(naverSearchService.search(command)).thenReturn(naverResponse);

        // then
        final SearchResponse results = sut.search(command);
        assertEquals(1, results.getDocuments().size());
        assertEquals("kakao", results.getDocuments().get(0).getTitle());
    }

    @Test
    @DisplayName("카카오 검색결과가 없을 때, 네이버 결과 반환")
    void test_search_whenKakaoIsFailedThenReturnNaverResults() {
        // given
        final SearchResponse kakaoResponse = new SearchResponse(null, Collections.EMPTY_LIST);
        final SearchResponse naverResponse = new SearchResponse(null, List.of(createSearchDocument("naver")));

        // when
        when(kakaoSearchService.search(command)).thenReturn(kakaoResponse);
        when(naverSearchService.search(command)).thenReturn(naverResponse);

        // then
        final SearchResponse results = sut.search(command);
        assertEquals(1, results.getDocuments().size());
        assertEquals("naver", results.getDocuments().get(0).getTitle());
    }

    @Test
    @DisplayName("카카오와 네이버 검색결과가 모두 없을 때, 빈 리스트 반환")
    void test_search_whenAllFailedThenReturnEmptyListResults() {
        // given
        final SearchResponse kakaoResponse = new SearchResponse(null, Collections.EMPTY_LIST);
        final SearchResponse naverResponse = new SearchResponse(null, Collections.EMPTY_LIST);

        // when
        when(kakaoSearchService.search(command)).thenReturn(kakaoResponse);
        when(naverSearchService.search(command)).thenReturn(naverResponse);

        // then
        final SearchResponse results = sut.search(command);
        assertTrue(results.getDocuments().isEmpty());
        assertEquals(1, results.getPage().getRequestPage());
        assertEquals(10, results.getPage().getRequestSize());
    }

    private SearchDocument createSearchDocument(final String title) {
        final SearchDocument document = new SearchDocument();
        document.setTitle(title);

        return document;
    }

}