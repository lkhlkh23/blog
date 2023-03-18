package bank.blog.remote.kakao;

import bank.blog.remote.kakao.dto.KakaoDocument;
import bank.blog.remote.kakao.dto.KakaoSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("h2")
@SpringBootTest
class KakaoClientTest {

    @Autowired
    private KakaoClient sut;

    @Test
    void test_search() {
        final KakaoSearchResponse response = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        assertTrue(response.getDocuments().isEmpty());
    }
}