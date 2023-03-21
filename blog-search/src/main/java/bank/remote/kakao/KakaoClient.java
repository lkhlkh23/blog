package bank.remote.kakao;

import bank.remote.kakao.dto.KakaoSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "kakao", url = "${feign.kakao.url}", fallbackFactory = KakaoClientFallbackFactory.class)
public interface KakaoClient {

    @GetMapping(value = "/v2/search/web", headers = "Authorization=${feign.kakao.key.rest}")
    KakaoSearchResponse search(@RequestParam(name = "query") String query,
                               @RequestParam(name = "sort") String sort,
                               @RequestParam(name = "page") int page,
                               @RequestParam(name = "size") int size);

}
