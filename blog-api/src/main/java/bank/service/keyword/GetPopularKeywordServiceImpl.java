package bank.service.keyword;

import bank.domain.keyword.PopularKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPopularKeywordServiceImpl implements GetPopularKeywordService {

    @Value("${spring.redis.key.keyword}")
    private String key;

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<PopularKeyword> getPopularKeywords(final int limit) {
        return redisTemplate.opsForZSet()
                            .reverseRangeWithScores(key, 0, limit - 1)
                            .stream()
                            .map(k -> new PopularKeyword((String) k.getValue(), k.getScore().longValue()))
                            .collect(Collectors.toList());

    }

}
