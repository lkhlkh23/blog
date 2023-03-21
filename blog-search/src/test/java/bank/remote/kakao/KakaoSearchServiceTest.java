package bank.remote.kakao;

import bank.domain.search.SearchResponse;
import bank.domain.search.SortType;
import bank.remote.kakao.dto.KakaoDocument;
import bank.remote.kakao.dto.KakaoMeta;
import bank.remote.kakao.dto.KakaoSearchResponse;
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

@SpringBootTest(classes = KakaoSearchServiceImpl.class)
class KakaoSearchServiceTest {

    @Autowired
    private KakaoSearchServiceImpl sut;

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
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
    }

    @Test
    @DisplayName("도큐먼트가 null 일 때, 빈 리스트 반환")
    void test_search_whenDocumentIsNullThenReturnEmpty() {
        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize()))
            .thenReturn(new KakaoSearchResponse());

        // then
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
    }

    @Test
    @DisplayName("응답이 null 일 때, 빈 리스트 반환")
    void test_search_whenResponseIsNullThenReturnEmpty() {
        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize())).thenReturn(null);

        // then
        final SearchResponse result = sut.search(command);
        assertNull(result.getDocuments());
    }

    @Test
    @DisplayName("응답과 도큐먼트가 값이 있을 때, 객체가 있는 리턴 반환")
    void test_search_whenResponseAndDocumentIsNotEmptyThenReturnList() {
        // given
        final KakaoSearchResponse response = new KakaoSearchResponse();
        final KakaoMeta meta = new KakaoMeta();
        meta.setTotalCount(100);
        meta.setIsEnd(false);
        final KakaoDocument document = new KakaoDocument();
        document.setTitle("title");
        document.setContents("contents");
        document.setUrl("url");
        response.setDocuments(List.of(document));
        response.setMeta(meta);

        // when
        when(kakaoClient.search(command.getQuery(), command.getSort().getKakoCode(), command.getPage(), command.getSize())).thenReturn(response);

        // then
        final SearchResponse result = sut.search(command);
        assertEquals(1, result.getPage().getRequestPage());
        assertEquals(10, result.getPage().getRequestSize());
        assertEquals("title", result.getDocuments().get(0).getTitle());
        assertEquals("contents", result.getDocuments().get(0).getContents());
        assertEquals("url", result.getDocuments().get(0).getUrl());
    }

}