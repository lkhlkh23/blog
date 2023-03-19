package bank.blog.service.keyword;

import bank.blog.domain.keyword.PopularKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPopularKeywordServiceImpl implements GetPopularKeywordService {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<PopularKeyword> getPopularKeywords() {
        return redisTemplate.opsForZSet()
                            .reverseRangeWithScores("keyword", 0, 9)
                            .stream()
                            .map(k -> new PopularKeyword((String) k.getValue(), k.getScore().longValue()))
                            .collect(Collectors.toList());

    }

}
