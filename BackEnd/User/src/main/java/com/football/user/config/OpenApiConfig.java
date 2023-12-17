package com.football.user.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("用户模块接口")
                        .description("用户模块接口描述")
                        .contact(new Contact().name("作者").email("邮箱").url(""))
                        .version("v1.0.0"));
    }
}
