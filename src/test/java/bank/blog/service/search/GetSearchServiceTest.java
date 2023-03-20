package bank.blog.service.search;

import bank.blog.domain.search.SearchDocument;
import bank.blog.domain.search.SortType;
import bank.blog.remote.kakao.KakaoSearchServiceImpl;
import bank.blog.remote.naver.NaverSearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
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
    void test_search_whenKakaoIsSuccessThenReturnKakaoResults() {
        // when
        when(kakaoSearchService.search(command)).thenReturn(List.of(createSearchDocument("kakao")));
        when(naverSearchService.search(command)).thenReturn(List.of(createSearchDocument("naver")));

        // then
        final List<SearchDocument> results = sut.search(command);
        assertEquals(1, results.size());
        assertEquals("kakao", results.get(0).getTitle());
    }

    @Test
    void test_search_whenKakaoIsFailedThenReturnNaverResults() {
        // when
        when(kakaoSearchService.search(command)).thenReturn(Collections.EMPTY_LIST);
        when(naverSearchService.search(command)).thenReturn(List.of(createSearchDocument("naver")));

        // then
        final List<SearchDocument> results = sut.search(command);
        assertEquals(1, results.size());
        assertEquals("naver", results.get(0).getTitle());
    }

    private SearchDocument createSearchDocument(final String title) {
        final SearchDocument document = new SearchDocument();
        document.setTitle(title);

        return document;
    }

}