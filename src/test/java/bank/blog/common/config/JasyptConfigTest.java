package bank.blog.common.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class JasyptConfigTest {

    @Autowired
    private StandardPBEStringEncryptor jasypt;

    @Test
    void test_jasyptConfig() {
        final String key = "KakaoAK d4364f4015d7f9f49fc50977d0896596";

        assertEquals(key, jasypt.decrypt(jasypt.encrypt(key)));
    }
}