package bank.blog.infra.keyword;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordJpaRepository extends JpaRepository<KeywordEntity, Long> {

    List<KeywordEntity> findAll(Sort sort);

}
