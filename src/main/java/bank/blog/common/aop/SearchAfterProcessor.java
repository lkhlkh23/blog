package bank.blog.common.aop;

import bank.blog.infra.keyword.KeywordEntity;
import bank.blog.infra.keyword.KeywordJpaRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
@Aspect
public class SearchAfterProcessor {

    @Autowired
    private KeywordJpaRepository keywordRepository;

    @Pointcut("execution(* bank.blog.controller.v1.GetBlogSearchController.search(..))")
    private void process() {

    }

    @AfterReturning(value = "process()")
    public void afterSuccess(final JoinPoint joinPoint) {
        if(isEmpty(joinPoint.getArgs()[0])) {
            return;
        }

        final String query = (String) joinPoint.getArgs()[0];
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
