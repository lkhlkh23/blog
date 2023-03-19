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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetBlogSearchControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("page가 1보다 작을 때, BadRequest 발생")
    void test_search_whenPageIsLessThenOneThenReturnBadRequest() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "01", -1, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("size 1보다 작을 때, BadRequest 발생")
    void test_search_whenSizeIsLessThenOneThenReturnBadRequest() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "01", 10, -1)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("page가 50보다 클 때, BadRequest 발생")
    void test_search_whenPageIsGreaterThen50ThenReturnBadRequest() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "01", 100, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("size 50보다 클 때, BadRequest 발생")
    void test_search_whenSizeIsGreaterThen50ThenReturnBadRequest() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "01", 10, 100)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }
}