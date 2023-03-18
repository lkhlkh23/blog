package bank.blog.service.keyword;

import bank.blog.domain.keyword.PopularKeyword;
import bank.blog.exception.KeywordNotFoundException;
import bank.blog.infra.keyword.GetPopularKeywordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetPopularKeywordServiceImpl implements GetPopularKeywordService {

    private final GetPopularKeywordRepository keywordRepository;

    @Cacheable(cacheNames = "getPopularKeywords", key = "#p0", unless = "#result.isEmpty()")
    @Transactional(readOnly = true)
    @Override
    public List<PopularKeyword> getPopularKeywords(final int limit) {
        final List<PopularKeyword> keywords = keywordRepository.getPopularKeywords()
                                                               .stream()
                                                               .limit(limit)
                                                               .collect(Collectors.toList());
        if(keywords.isEmpty()) {
            throw new KeywordNotFoundException();
        }

        return keywords;
    }

}
