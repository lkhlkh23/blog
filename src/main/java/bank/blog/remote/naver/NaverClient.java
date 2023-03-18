package bank.blog.remote.naver;

import bank.blog.remote.naver.dto.NaverSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naver", url = "${feign.naver.url}", fallbackFactory = NaverClientFallbackFactory.class)
public interface NaverClient {

    @GetMapping(value = "/v1/search/blog", headers = {"X-Naver-Client-Id=${feign.naver.key.id}", "X-Naver-Client-Secret=${feign.naver.key.secret}"})
    NaverSearchResponse search(@RequestParam(name = "query") String query,
                               @RequestParam(name = "sort") String sort,
                               @RequestParam(name = "start") int start,
                               @RequestParam(name = "display") int display);

}
