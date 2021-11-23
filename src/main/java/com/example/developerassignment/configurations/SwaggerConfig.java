package com.example.developerassignment.configurations;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Products order API")
                        .description("<b>REST / JSON </b> web service in Java using Spring Boot (RestController) with an API that\n" +
                                "supports basic products CRUD:\n <br>" +
                                "● Create a new product\n <br>" +
                                "● Get a list of all products\n <br>" +
                                "● Update a product\n <br>" +
                                "● Placing an order\n <br>" +
                                "● Retrieving all orders within a given time period\n <br>")
                        .version("0.0.1-SNAPSHOT")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
