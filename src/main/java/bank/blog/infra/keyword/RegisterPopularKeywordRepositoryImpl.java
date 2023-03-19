package bank.blog.infra.keyword;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class RegisterPopularKeywordRepositoryImpl implements RegisterPopularKeywordRepository {

    private final KeywordJpaRepository keywordRepository;

    @Override
    public void processIfPresentOrNot(final String query) {
        final Optional<KeywordEntity> saved = keywordRepository.findKeywordEntityByKeyword(query);
        if(!saved.isPresent()) {
            final LocalDateTime now = LocalDateTime.now();
            keywordRepository.saveAndFlush(KeywordEntity.builder()
                                                        .keyword(query)
                                                        .total(1)
                                                        .createdAt(now)
                                                        .updatedAt(now)
                                                        .build());
            return;
        }

        final KeywordEntity keyword = saved.get();
        keyword.setTotal(keyword.getTotal() + 1);
        keywordRepository.saveAndFlush(keyword);
    }

}
