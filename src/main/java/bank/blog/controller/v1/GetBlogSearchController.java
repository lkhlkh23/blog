package bank.blog.controller.v1;

import bank.blog.controller.v1.dto.BlogSearchBundleV1;
import bank.blog.controller.v1.dto.ResponseV1;
import bank.blog.controller.v1.mapper.BlogSearchV1Mapper;
import bank.blog.domain.search.SortType;
import bank.blog.service.search.GetSearchService;
import bank.blog.service.search.SearchCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao/v1")
public class GetBlogSearchController {

    private final GetSearchService searchService;

    @Operation(summary = "블로그 검색", description = "블로그 검색 (1순위 : 카카오, 2순위 : 네이버)", tags = {"GetBlogSearchController" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/search")
    public ResponseV1<BlogSearchBundleV1> search(@RequestParam(name = "query") String query,
                                                 @RequestParam(name = "sort", defaultValue = "01") SortType sort,
                                                 @RequestParam(name = "page", defaultValue = "1") @Min(1) @Max(50) int page,
                                                 @RequestParam(name = "size", defaultValue = "10") @Min(1) @Max(50) int size) {
        final SearchCommand command = SearchCommand.builder()
                                                   .query(query)
                                                   .sort(sort)
                                                   .page(page)
                                                   .size(size)
                                                   .build();

        final BlogSearchBundleV1 blogSearches = new BlogSearchBundleV1();
        searchService.search(command)
                     .stream()
                     .map(BlogSearchV1Mapper.INSTANCE::from)
                     .forEach(blogSearches::addSearch);

        return new ResponseV1<>(blogSearches, HttpStatus.OK.value(), "");
    }

}
