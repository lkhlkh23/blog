package bank.service.keyword;

import bank.domain.keyword.PopularKeyword;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("h2")
@SpringBootTest
class GetPopularKeywordServiceTest {

    @Autowired
    private GetPopularKeywordService sut;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate.delete("keyword");
    }

    @Test
    @DisplayName("키워드 조회 결과가 없을 때, 빈 리스트 리턴")
    void test_getPopularKeywords_whenNotExistKeywordThenReturnEmpty() {
        assertTrue(sut.getPopularKeywords(10).isEmpty());
    }

    @Test
    @DisplayName("키워드 조회 결과가 10개 이상일 때, 10개의 데이터만 리턴")
    void test_getPopularKeywords_whenExistKeywordOver10DataThenReturn10Data() {
        // when
        for (int i = 1; i <= 20; i++) {
            redisTemplate.opsForZSet().add("keyword", "kakao-" + i, i);
        }

        // then
        final List<PopularKeyword> result = sut.getPopularKeywords(10);
        assertEquals(10, result.size());
        assertEquals("kakao-20", result.get(0).getKeyword());
        assertEquals("kakao-11", result.get(9).getKeyword());
    }

    @Test
    @DisplayName("키워드 조회 결과가 10개 미만일 때, (5개), 5개의 데이터만 리턴")
    void test_getPopularKeywords_whenExistKeywordLessThan10DataThenReturnData() {
        // when
        for (int i = 1; i <= 5; i++) {
            redisTemplate.opsForZSet().add("keyword", "kakao-" + i, i);
        }

        // then
        final List<PopularKeyword> result = sut.getPopularKeywords(10);
        assertEquals(5, result.size());
        assertEquals("kakao-5", result.get(0).getKeyword());
        assertEquals("kakao-1", result.get(4).getKeyword());
    }

}