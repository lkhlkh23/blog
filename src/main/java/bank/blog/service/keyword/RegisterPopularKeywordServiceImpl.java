package bank.blog.service.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterPopularKeywordServiceImpl implements RegisterPopularKeywordService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void processIfPresentOrNot(final String query) {
        try {
            redisTemplate.opsForZSet().incrementScore("keyword", query, 1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
