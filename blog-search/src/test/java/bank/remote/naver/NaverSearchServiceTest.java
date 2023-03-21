package bank.remote.naver;

import bank.domain.search.SearchResponse;
import bank.domain.search.SortType;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {NaverSearchServiceImpl.class})
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
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
    }

    @Test
    @DisplayName("아이템이 null 일 때, 빈 리스트 반환")
    void test_search_whenItemIsNullThenReturnEmpty() {
        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize()))
            .thenReturn(new NaverSearchResponse());

        // then
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        // when
        when(naverClient.search(command.getQuery(), command.getSort().getNaverCode(), command.getPage(), command.getSize())).thenReturn(null);

        // then
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
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
        final SearchResponse result = sut.search(command);
        assertEquals(1, result.getPage().getRequestPage());
        assertEquals(10, result.getPage().getRequestSize());
        assertEquals(1, result.getDocuments().size());
        assertEquals("title", result.getDocuments().get(0).getTitle());
        assertEquals("description", result.getDocuments().get(0).getContents());
        assertEquals("link", result.getDocuments().get(0).getUrl());
    }

}