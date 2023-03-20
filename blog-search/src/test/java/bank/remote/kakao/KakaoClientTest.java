package bank.remote.kakao;

import bank.remote.kakao.dto.KakaoDocument;
import bank.remote.kakao.dto.KakaoSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("h2")
@SpringBootTest
class KakaoClientTest {

    @Autowired
    private KakaoClient sut;

    @Test
    void test_search() {
        final KakaoSearchResponse response = sut.search("카카오뱅크채용", "accuracy", 1, 10);
        final List<KakaoDocument> documents = response.getDocuments();

        assertFalse(documents.isEmpty());
        for (final KakaoDocument document : documents) {
            assertNotNull(document.getTitle());
            assertNotNull(document.getContents());
            assertNotNull(document.getUrl());
        }
    }
}