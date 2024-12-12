package com.prueba.proteccion.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("bearerAuth")
                .description("JWT auth description")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .components(new Components().addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement)
                .info(new Info()
                        .title("API protegida con JWT")
                        .version("1.0")
                        .description("Documentación para una API protegida con JWT")
                        .contact(new Contact()
                                .name("Guille")
                                .email("guille-dev@email.com")
                                .url("https://www.ejemplo.com")
                        )
                        .license(new License()
                                .name("License name")
                                .url("https://www.some-url.com")
                        )
                        .termsOfService("Terms of service")
                )
                .servers(
                        List.of(
                                new Server()
                                        .url("http://localhost:8080")
                                        .description("Local server"),
                                new Server()
                                        .url("https://api.ejemplo.com/v1")
                                        .description("Production server"),
                                new Server()
                                        .url("https://staging.ejemplo.com/v1")
                                        .description("Staging server")
                        )
                );
    }


}