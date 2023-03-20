package bank.remote.naver;

import bank.common.type.SortType;
import bank.domain.search.SearchDocument;
import bank.remote.naver.dto.NaverItem;
import bank.remote.naver.dto.NaverSearchResponse;
import bank.service.search.SearchCommand;
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

@SpringBootTest
class NaverSearchServiceTest {

    @Autowired
    private NaverSearchServiceImpl sut;

    @MockBean
    private NaverClient naverClient;

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
    @DisplayName("아이템이 비었을 때, 빈 리스트 반환")
    void test_search_whenItemIsEmptyThenReturnEmpty() {
        // given
        final NaverSearchResponse response = new NaverSearchResponse();
        response.setItems(Collections.EMPTY_LIST);

        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize())).thenReturn(response);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("아이템이 null 일 때, 빈 리스트 반환")
    void test_search_whenItemIsNullThenReturnEmpty() {
        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize()))
            .thenReturn(new NaverSearchResponse());

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize())).thenReturn(null);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertTrue(searchDocuments.isEmpty());
    }

    @Test
    @DisplayName("응답과 아이템이 값이 있을 때, 객체가 있는 리턴 반환")
    void test_search_whenResponseAndItemIsNotEmptyThenReturnList() {
        // given
        final NaverSearchResponse response = new NaverSearchResponse();
        final NaverItem item = new NaverItem();
        item.setTitle("title");
        item.setDescription("description");
        item.setLink("link");
        response.setItems(List.of(item));

        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize())).thenReturn(response);

        // then
        final List<SearchDocument> searchDocuments = sut.search(command);
        assertEquals(1, searchDocuments.size());
        assertEquals("title", searchDocuments.get(0).getTitle());
        assertEquals("description", searchDocuments.get(0).getContents());
        assertEquals("link", searchDocuments.get(0).getUrl());
    }

}