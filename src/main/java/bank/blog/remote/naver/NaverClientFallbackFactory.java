package bank.blog.remote.naver;

import bank.blog.remote.naver.dto.NaverSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NaverClientFallbackFactory implements FallbackFactory<NaverClient> {

    @Override
    public NaverClient create(Throwable cause) {
        return (query, sort, page, size) -> {
            log.error("naver 'search' method is failed, query : {}, sort : {}, page :{}, size :{}", query, sort, page, size, cause.getCause());
            return new NaverSearchResponse();
        };
    }

}
