package bank.blog.infra.keyword;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KeywordJpaRepository extends JpaRepository<KeywordEntity, Long> {

    List<KeywordEntity> findAll(Sort sort);

    Optional<KeywordEntity> findKeywordEntityByKeyword(final String keyword);

}
