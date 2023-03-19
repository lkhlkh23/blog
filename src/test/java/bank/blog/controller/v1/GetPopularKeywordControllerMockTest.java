package bank.blog.controller.v1;

import bank.blog.controller.v1.dto.ResponseV1;
import bank.blog.exception.KeywordNotFoundException;
import bank.blog.service.keyword.GetPopularKeywordService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ActiveProfiles("h2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetPopularKeywordControllerMockTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private GetPopularKeywordService keywordService;

    @Test
    @DisplayName("파마미터 limit 이 0이하일 경우 400 메세지 리턴")
    void test_getPopularKeywords_whenLimitIsLessThanZeroThenReturn400() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange("/kakao/v1/keywords?limit=0"
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().getStatus());
    }

    @Test
    @DisplayName("키워드가 없을 때 204 메세지 리턴")
    void test_getPopularKeywords_whenNotExistKeywordThenReturn204() {
        when(keywordService.getPopularKeywords(10)).thenThrow(KeywordNotFoundException.class);

        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange("/kakao/v1/keywords?limit"
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(204, response.getBody().getStatus());
    }

}