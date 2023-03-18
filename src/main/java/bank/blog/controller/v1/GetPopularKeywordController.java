package bank.blog.controller.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao/v1")
public class GetPopularKeywordController {

    @Operation(summary = "인기검색어", description = "인기검색어 top 10 조회", tags = { "GetPopularKeywordController" })
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "OK",
                     content = @Content(schema = @Schema(implementation = String.class))),
        @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        @ApiResponse(responseCode = "404", description = "NOT FOUND"),
        @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    })
    @GetMapping(value = "/keywords")
    public String getPopularKeywords(@RequestParam(name = "limit", defaultValue = "10") int limit) {
        return "test";
    }

}
