package bank.blog.remote.kakao;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.kakao.dto.KakaoDocument;
import bank.blog.remote.kakao.dto.KakaoSearchResponse;
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

    @Test
    @DisplayName("도큐먼트가 비었을 때, 빈 리스트 반환")
    void test_search_whenDocumentIsEmptyThenReturnEmpty() {
        final KakaoSearchResponse response = new KakaoSearchResponse();
        response.setDocuments(Collections.EMPTY_LIST);

        when(kakaoClient.search("카카오뱅크채용", "accuracy", 1, 10)).thenReturn(response);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("도큐먼트가 null 일 때, 빈 리스트 반환")
    void test_search_whenDocumentIsNullThenReturnEmpty() {
        when(kakaoClient.search("카카오뱅크채용", "accuracy", 1, 10)).thenReturn(new KakaoSearchResponse());

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        when(kakaoClient.search("카카오뱅크채용", "accuracy", 1, 10)).thenReturn(null);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답과 도큐먼트가 값이 있을 때, 객체가 있는 리턴 반환")
    void test_search_whenResponseAndDocumentIsNotEmptyThenReturnList() {
        final KakaoSearchResponse response = new KakaoSearchResponse();
        final KakaoDocument document = new KakaoDocument();
        document.setTitle("title");
        document.setContents("contents");
        document.setUrl("url");
        response.setDocuments(List.of(document));

        when(kakaoClient.search("카카오뱅크채용", "accuracy", 1, 10)).thenReturn(response);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        assertEquals(1, searchDocuments.size());
        assertEquals("title", searchDocuments.get(0).getTitle());
        assertEquals("contents", searchDocuments.get(0).getContents());
        assertEquals("url", searchDocuments.get(0).getUrl());
    }

}