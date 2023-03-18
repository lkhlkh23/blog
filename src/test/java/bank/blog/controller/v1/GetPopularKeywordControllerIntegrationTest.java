package bank.blog.controller.v1;

import bank.blog.controller.v1.dto.PopularKeywordBundleV1;
import bank.blog.controller.v1.dto.PopularKeywordV1;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("h2")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetPopularKeywordControllerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("키워드 10개 조회 시, 성공적으로 데이터 반환")
    void test_getPopularKeywords_whenSearch10KeywordThenReturnOK() {
        final ResponseEntity<ResponseV1<PopularKeywordBundleV1>> response =
            this.restTemplate.exchange("/kakao/v1/keywords"
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<PopularKeywordBundleV1>>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(200, response.getBody().getStatus());
        final List<PopularKeywordV1> keywords = response.getBody().getData().getKeywords();
        assertEquals(10, keywords.size());
        assertEquals(11, keywords.get(0).getTotal());
        assertEquals(2, keywords.get(9).getTotal());
    }

    @Test
    @DisplayName("키워드 -1개 조회 시, 예외 발생")
    void test_getPopularKeywords_whenSearchKeywordMinusOneThenReturnBadRequest() {
        final ResponseEntity<ResponseV1<Void>> response =
            this.restTemplate.exchange("/kakao/v1/keywords?limit=-1"
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<Void>>() {
                });

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}