package bank.remote.naver;

import bank.remote.naver.dto.NaverItem;
import bank.remote.naver.dto.NaverSearchResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class NaverClientTest {

    @Autowired
    private NaverClient sut;

    @Test
    void test_search() {
        final NaverSearchResponse response = sut.search("카카오채용", "sim", 1, 10);
        assertTrue(response.getDisplay() > 0);
        assertTrue(response.getStart() > 0);
        assertTrue(response.getTotal() > 0);
        final List<NaverItem> items = response.getItems();
        for (final NaverItem item : items) {
            assertNotNull(item.getBloggerLink());
            assertNotNull(item.getBloggerName());
            assertNotNull(item.getLink());
            assertNotNull(item.getTitle());
        }
    }

}