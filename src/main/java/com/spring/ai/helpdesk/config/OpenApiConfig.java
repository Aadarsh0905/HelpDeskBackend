package com.spring.ai.helpdesk.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI helpDeskOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HelpDeskBackend API")
                        .description("AI-powered help desk backend with ticket management and assistant APIs")
                        .version("1.0.0"));
    }
}
