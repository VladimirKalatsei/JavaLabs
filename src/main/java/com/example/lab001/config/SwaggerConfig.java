package com.example.lab001.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springdoc.core.customizers.OperationCustomizer;

@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Java Lab Swagger API")
                        .description("API для лабораторной работы по Java")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Kalatsei Vladimir")
                                .url("https://t.me/vovkq")
                                .email("vovkqh@gmail.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }

    @Bean
    public OperationCustomizer customGlobalHeaders() {
        return (operation, handlerMethod) -> {
            operation.addParametersItem(new io.swagger.v3.oas.models.parameters.Parameter()
                    .name("X-Global-Header")
                    .description("Lab 4")
                    .required(false)
                    .in("header")
                    .schema(new io.swagger.v3.oas.models.media.StringSchema()));
            return operation;
        };
    }
}