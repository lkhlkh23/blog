package bank.controller.v1;

import bank.controller.v1.dto.BlogSearchBundleV1;
import bank.controller.v1.dto.ResponseV1;
import bank.domain.search.SearchDocument;
import bank.domain.search.SearchResponse;
import bank.domain.search.SortType;
import bank.service.search.GetSearchService;
import bank.service.search.SearchCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetBlogSearchControllerIntegrationTest {

    @Value("${spring.redis.key.keyword}")
    private String key;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private GetSearchService searchService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    private SearchCommand command;

    @BeforeEach
    void setUp() {
        redisTemplate.delete(key);
        this.command = SearchCommand.builder()
                                    .query("kakao")
                                    .page(1)
                                    .size(10)
                                    .sort(SortType.ACCURACY)
                                    .build();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 51})
    @DisplayName("page가 1보다 직거나 50보다 클 때, internal server error 발생")
    void test_search_whenPageIsLessThen1OrGreaterThen50ThenReturn500(final int param) {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "accuracy", param, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 51})
    @DisplayName("size 1보다 작거나 50보다 클 때, internal server error 발생")
    void test_search_whenSizeIsLessThen1OrGreaterThen50ThenReturn500(final int param) {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "accuracy", 10, param)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("정렬조건에 오타가 있을 때, internal server error 발생")
    void test_search_whenSortIsMissSpellThenReturn500() {
        final ResponseEntity<ResponseV1> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "miss", 0, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1>() {
                });

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(500, response.getBody().getStatus());
    }

    @Test
    @DisplayName("검색결과가 없을 때, 빈 검색결과 반환하고, 캐시에 키워드가 존재할 때, 조회수 1증가")
    void test_search_whenNotExistSearchResultAndExistCacheKeywordThenReturnEmptyListAndAddCacheTotal() {
        // when
        when(searchService.search(command)).thenReturn(new SearchResponse(null, Collections.EMPTY_LIST));
        redisTemplate.opsForZSet().add(key, "kakao", 100);

        // then
        final ResponseEntity<ResponseV1<BlogSearchBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "accuracy", 1, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<BlogSearchBundleV1>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getData().getSearches());
        assertEquals(101.0, redisTemplate.opsForZSet().score(key, "kakao"));
    }

    @Test
    @DisplayName("검색결과가 없을 때, 빈 검색결과 반환하고, 키워드가 존재하지 않을 때, 조회수 1")
    void test_search_whenNotExistSearchResultAndCacheKeywordThenReturnEmptyListAndCacheTotalIsOne() {
        // when
        when(searchService.search(command)).thenReturn(new SearchResponse(null, Collections.EMPTY_LIST));

        // then
        final ResponseEntity<ResponseV1<BlogSearchBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "accuracy", 1, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<BlogSearchBundleV1>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertNull(response.getBody().getData().getSearches());
        assertEquals(1.0, redisTemplate.opsForZSet().score(key, "kakao"));
    }

    @Test
    @DisplayName("검색결과가 있을 때, 검색결과 반환하고, 키워드가 존재할 때, 조회수 1증가")
    void test_search_whenExistSearchResultAndCacheKeywordThenReturnDataListAndAddCacheTotal() {
        // when
        when(searchService.search(command)).thenReturn(new SearchResponse(null, List.of(createSearchDocument("무지"))));
        redisTemplate.opsForZSet().add(key, "kakao", 100);

        // then
        final ResponseEntity<ResponseV1<BlogSearchBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "ACCURACY", 1, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<BlogSearchBundleV1>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getSearches().size());
        assertEquals("무지", response.getBody().getData().getSearches().get(0).getTitle());
        assertEquals(101.0, redisTemplate.opsForZSet().score(key, "kakao"));
    }

    @Test
    @DisplayName("검색결과가 있을 때, 검색결과 반환하고, 키워드가 존재하지 않을 때, 조회수 1")
    void test_search_whenExistSearchResultAndNotExistCacheKeywordThenReturnDataAndCacheTotalIsOne() {
        // when
        when(searchService.search(command)).thenReturn(new SearchResponse(null, List.of(createSearchDocument("무지"))));

        // then
        final ResponseEntity<ResponseV1<BlogSearchBundleV1>> response =
            this.restTemplate.exchange(String.format("/kakao/v1/search?query=%s&sort=%s&page=%d&size=%d", "kakao", "accuracy", 1, 10)
                , HttpMethod.GET, null, new ParameterizedTypeReference<ResponseV1<BlogSearchBundleV1>>() {
                });
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(HttpStatus.OK.value(), response.getBody().getStatus());
        assertNotNull(response.getBody().getData());
        assertEquals(1, response.getBody().getData().getSearches().size());
        assertEquals("무지", response.getBody().getData().getSearches().get(0).getTitle());
        assertEquals(1.0, redisTemplate.opsForZSet().score(key, "kakao"));
    }

    private SearchDocument createSearchDocument(final String title) {
        final SearchDocument document = new SearchDocument();
        document.setTitle(title);

        return document;
    }

}