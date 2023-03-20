package bank.common.aop;

import bank.service.keyword.RegisterPopularKeywordService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;

@Component
@Aspect
public class SearchAfterProcessor {

    @Autowired
    private RegisterPopularKeywordService keywordService;

    @Pointcut("execution(* bank.controller.v1.GetBlogSearchController.search(..))")
    private void process() {

    }

    @AfterReturning(value = "process()")
    public void afterSuccess(final JoinPoint joinPoint) {
        if(isEmpty(joinPoint.getArgs()[0])) {
            return;
        }

        keywordService.processIfPresentOrNot((String) joinPoint.getArgs()[0]);
    }

}
