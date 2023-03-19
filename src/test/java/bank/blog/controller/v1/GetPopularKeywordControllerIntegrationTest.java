package bank.blog.controller.v1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("h2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetPopularKeywordControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @BeforeEach
    void setUp() {

    }

    @Test
    @DisplayName("키워드 10개 조회 시, 성공적으로 데이터 반환")
    void test_getPopularKeywords_whenSearch10KeywordThenReturnOK() {

    }

    @Test
    @DisplayName("키워드가 없을 때 204 메세지 리턴")
    void test_getPopularKeywords_whenNotExistKeywordThenReturn204() {

    }

}