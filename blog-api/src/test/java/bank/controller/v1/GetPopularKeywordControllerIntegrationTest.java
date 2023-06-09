package bank.controller.v1;

import bank.controller.v1.dto.PopularKeywordBundleV1;
import bank.controller.v1.dto.PopularKeywordV1;
import bank.controller.v1.dto.ResponseV1;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetPopularKeywordControllerIntegrationTest {

    @Value("${spring.redis.key.keyword}")
    private String key;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {
        redisTemplate.delete(key);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 11})
    @DisplayName("1보다 작거나 10보다 크면, 에러발생")
    void test_getPopularKeywords_whenParameterIsLessThen1OrOverThan10ThenReturn500Error() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/keywords?limit=%d", 11)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("키워드가 없을 때, null 키워드리스트 반환")
    void test_getPopularKeywords_whenNotExistKeywordThenReturnNullKeywordList() {
        final ResponseEntity<ResponseV1<PopularKeywordBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/keywords?limit=%d", 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<PopularKeywordBundleV1>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getData().getKeywords());
    }

    @Test
    @DisplayName("키워드가 존재할 때, 키워드 10개 조회 시, 성공적으로 데이터 반환")
    void test_getPopularKeywords_whenSearch10KeywordThenReturnData() {
        // when
        for (int i = 1; i <= 20; i++) {
            redisTemplate.opsForZSet().add(key, "kakao-" + i, i);
        }

        // then
        final ResponseEntity<ResponseV1<PopularKeywordBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/keywords?limit=%d", 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<PopularKeywordBundleV1>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        final List<PopularKeywordV1> keywords = response.getBody().getData().getKeywords();
        assertFalse(keywords.isEmpty());
        assertEquals("kakao-20", keywords.get(0).getKeyword());
        assertEquals(20, keywords.get(0).getTotal());
    }

    @Test
    @DisplayName("키워드가 2개 존재할 때, 키워드 3개 조회, 성공적으로 데이터 반환")
    void test_getPopularKeywords_whenExistOnly2KeywordThenReturnData() {
        // when
        redisTemplate.opsForZSet().add(key, "naver", 2);
        redisTemplate.opsForZSet().add(key, "kakao", 10);

        // then
        final ResponseEntity<ResponseV1<PopularKeywordBundleV1>> response =
            this.restTemplate.exchange("/kakao/v1/keywords"
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<PopularKeywordBundleV1>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        final List<PopularKeywordV1> keywords = response.getBody().getData().getKeywords();
        assertFalse(keywords.isEmpty());
        assertEquals("kakao", keywords.get(0).getKeyword());
        assertEquals(10, keywords.get(0).getTotal());
        assertEquals("naver", keywords.get(1).getKeyword());
        assertEquals(2, keywords.get(1).getTotal());
    }

}