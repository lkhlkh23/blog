package bank.service.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPopularKeywordServiceImpl implements RegisterPopularKeywordService {

    @Value("${spring.redis.key.keyword}")
    private String key;


    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void processIfPresentOrNot(final String query) {
        try {
            redisTemplate.opsForZSet().incrementScore(key, query, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
