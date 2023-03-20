package bank.blog.controller.v1;

import bank.blog.controller.v1.dto.PopularKeywordBundleV1;
import bank.blog.controller.v1.dto.ResponseV1;
import bank.blog.controller.v1.mapper.PopularKeywordV1Mapper;
import bank.blog.exception.InvalidParameterException;
import bank.blog.service.keyword.GetPopularKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao/v1")
public class GetPopularKeywordController {

    private final GetPopularKeywordService getPopularKeywordService;

    @Operation(summary = "인기검색어", description = "인기검색어 조회", tags = { "GetPopularKeywordController" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/keywords")
    public ResponseV1<PopularKeywordBundleV1> getPopularKeywords(@RequestParam(name = "limit", defaultValue = "5") int limit) {
        if(limit < 1 || limit > 10) {
            throw new InvalidParameterException("limit 은 1 이상 10 이하이아야만 합니다");
        }

        final PopularKeywordBundleV1 keywords = new PopularKeywordBundleV1();
        getPopularKeywordService.getPopularKeywords(limit)
                                .stream()
                                .map(PopularKeywordV1Mapper.INSTANCE::from)
                                .forEach(keywords::addKeyword);

        return new ResponseV1<>(keywords, HttpStatus.OK.value(), "");
    }

}
