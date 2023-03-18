package bank.blog.service.keyword;

import bank.blog.domain.keyword.PopularKeyword;
import bank.blog.exception.KeywordNotFoundException;
import bank.blog.infra.keyword.GetPopularKeywordRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class GetPopularKeywordServiceTest {

    @Autowired
    private GetPopularKeywordService sut;

    @MockBean
    private GetPopularKeywordRepository repository;

    @Test
    @DisplayName("키워드 조회 결과가 없을 때, 예외 발생")
    void test_getPopularKeywords_whenNotExistKeywordThenOccurException() {
        when(repository.getPopularKeywords()).thenReturn(Collections.EMPTY_LIST);

        assertThrows(KeywordNotFoundException.class, () -> sut.getPopularKeywords(10));
    }

    @Test
    @DisplayName("키워드 조회 결과가 10개 이상일 때, 10개의 데이터만 추출")
    void test_getPopularKeywords_whenExistKeywordOver10DataThenReturn10Data() {
        final List<PopularKeyword> keywords = new ArrayList<>();
        for (int i = 15; i > 0; i--) {
            keywords.add(createPopularKeyword("word-" + i, i));
        }

        when(repository.getPopularKeywords()).thenReturn(keywords);

        final List<PopularKeyword> result = sut.getPopularKeywords(10);
        assertEquals(10, result.size());
        assertEquals("word-15", result.get(0).getKeyword());
        assertEquals("word-6", result.get(9).getKeyword());
    }

    private PopularKeyword createPopularKeyword(final String word, final int total) {
        final PopularKeyword keyword = new PopularKeyword();
        keyword.setKeyword(word);
        keyword.setTotal(total);

        return keyword;
    }

}