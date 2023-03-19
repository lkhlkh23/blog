package bank.blog.infra.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class RegisterPopularKeywordRepositoryImpl implements RegisterPopularKeywordRepository {

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
