package bank.blog.controller.v1;

import bank.blog.controller.v1.dto.ResponseV1;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("h2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetPopularKeywordControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

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

}