package bank.blog.infra.keyword;

import bank.blog.domain.keyword.PopularKeyword;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class GetPopularKeywordRepositoryImpl implements GetPopularKeywordRepository {

    private final KeywordJpaRepository keywordJpaRepository;

    @Override
    public List<PopularKeyword> getPopularKeywords() {
        return keywordJpaRepository.findAll(Sort.by(Sort.Direction.ASC, "total"))
                                   .stream()
                                   .map(PopularKeywordMapper.INSTANCE::from)
                                   .collect(Collectors.toList());
    }

}
