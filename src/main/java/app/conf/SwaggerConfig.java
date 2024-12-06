package app.conf;

import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("public-api")
                .packagesToScan("app.controller")
                .addOpenApiCustomiser(openApi -> openApi.info(new Info()
                        .title("Produto API")
                        .version("v1")
                        .description("API para gerenciamento de produtos.")))
                .build();
    }
}
