package bank.blog.remote.naver;

import bank.blog.domain.search.SearchDocument;
import bank.blog.remote.common.RemoteSearchService;
import bank.blog.remote.naver.dto.NaverItem;
import bank.blog.remote.naver.dto.NaverSearchResponse;
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
class NaverSearchServiceTest {

    @Autowired
    @Qualifier("naverSearchService")
    private RemoteSearchService sut;

    @MockBean
    private NaverClient naverClient;

    @Test
    @DisplayName("아이템이 비었을 때, 빈 리스트 반환")
    void test_search_whenItemIsEmptyThenReturnEmpty() {
        final NaverSearchResponse response = new NaverSearchResponse();
        response.setItems(Collections.EMPTY_LIST);

        when(naverClient.search("카카오뱅크채용", "sim", 1, 10)).thenReturn(response);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "sim", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("아이템이 null 일 때, 빈 리스트 반환")
    void test_search_whenItemIsNullThenReturnEmpty() {
        when(naverClient.search("카카오뱅크채용", "sim", 1, 10)).thenReturn(new NaverSearchResponse());

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "sim", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        when(naverClient.search("카카오뱅크채용", "sim", 1, 10)).thenReturn(null);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "sim", 1, 10);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답과 아이템이 값이 있을 때, 객체가 있는 리턴 반환")
    void test_search_whenResponseAndItemIsNotEmptyThenReturnList() {
        final NaverSearchResponse response = new NaverSearchResponse();
        final NaverItem item = new NaverItem();
        item.setTitle("title");
        item.setDescription("description");
        item.setLink("link");
        response.setItems(List.of(item));

        when(naverClient.search("카카오뱅크채용", "sim", 1, 10)).thenReturn(response);

        final List<SearchDocument> searchDocuments = sut.search("카카오뱅크채용", "sim", 1, 10);
        assertEquals(1, searchDocuments.size());
        assertEquals("title", searchDocuments.get(0).getTitle());
        assertEquals("description", searchDocuments.get(0).getContents());
        assertEquals("link", searchDocuments.get(0).getUrl());
    }

}