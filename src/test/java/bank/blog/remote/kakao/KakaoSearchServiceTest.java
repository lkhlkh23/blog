package bank.blog.remote.kakao;

import bank.blog.domain.search.SearchDocument;
import bank.blog.domain.search.SortType;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.kakao.dto.KakaoDocument;
import bank.blog.remote.kakao.dto.KakaoSearchResponse;
import bank.blog.service.search.SearchCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class KakaoSearchServiceTest {

    @Autowired
    @Qualifier("kakaoSearchService")
    private RemoteSearchService sut;

    @MockBean
    private KakaoClient kakaoClient;

    private SearchCommand command;

    @BeforeEach
    void setUp() {
        this.command = SearchCommand.builder()
                                    .query("카카오")
                                    .sort(SortType.ACCURACY)
                                    .size(10)
                                    .page(1)
                                    .build();
    }

    @Test
    @DisplayName("도큐먼트가 비었을 때, 빈 리스트 반환")
    void test_search_whenDocumentIsEmptyThenReturnEmpty() {
        // given
        final KakaoSearchResponse response = new KakaoSearchResponse();
        response.setDocuments(Collections.EMPTY_LIST);

        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize())).thenReturn(response);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("도큐먼트가 null 일 때, 빈 리스트 반환")
    void test_search_whenDocumentIsNullThenReturnEmpty() {
        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize()))
            .thenReturn(new KakaoSearchResponse());

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize())).thenReturn(null);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답과 도큐먼트가 값이 있을 때, 객체가 있는 리턴 반환")
    void test_search_whenResponseAndDocumentIsNotEmptyThenReturnList() {
        // given
        final KakaoSearchResponse response = new KakaoSearchResponse();
        final KakaoDocument document = new KakaoDocument();
        document.setTitle("title");
        document.setContents("contents");
        document.setUrl("url");
        response.setDocuments(List.of(document));

        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize())).thenReturn(response);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertEquals(1, searchDocuments.size());
        assertEquals("title", searchDocuments.get(0).getTitle());
        assertEquals("contents", searchDocuments.get(0).getContents());
        assertEquals("url", searchDocuments.get(0).getUrl());
    }

}