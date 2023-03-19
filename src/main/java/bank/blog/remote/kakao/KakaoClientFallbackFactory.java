package bank.blog.remote.kakao;

import bank.blog.remote.kakao.dto.KakaoSearchResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KakaoClientFallbackFactory implements FallbackFactory<KakaoClient> {

    @Override
    public KakaoClient create(Throwable cause) {
        return (query, sort, page, size) -> {
            log.error("kakao 'search' method is failed, query : {}, sort : {}, page :{}, size :{}", query, sort, page, size, cause.getCause());
            return new KakaoSearchResponse();
        };
    }

}
