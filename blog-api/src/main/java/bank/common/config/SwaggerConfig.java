package bank.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
    info = @Info(title = "블로그 검색 API 명세서",
                 description = "블로그 검색/인기검색어 API 명세서",
                 version = "v1"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        final String[] paths = {"/kakao/v1/**"};

        return GroupedOpenApi.builder()
                             .group("블로그 검색 API v1")
                             .pathsToMatch(paths)
                             .build();
    }
}
