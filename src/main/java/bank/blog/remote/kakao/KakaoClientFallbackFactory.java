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
        return (query, name, page, size) -> {
            log.error("Kakao 'search' method is failed");
            return new KakaoSearchResponse();
        };
    }

}
