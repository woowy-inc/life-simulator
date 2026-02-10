package ru.woowy.application.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
internal class OpenApiConfig(
    private val authenticationScheme: AuthenticationScheme,
) {
    @Bean
    fun openApi(): OpenAPI {
        val info =
            Info()
                .title("Woowy LifeSim: Auth Service")
                .description("Woowy LifeSim: Auth Service description")
                .contact(Contact().name("Denis").email("dnartysh@yandex.ru"))
                .summary("Woowy summary")
                .version("0.1.0")

        val server =
            Server()
                .url("http://localhost:9393") // TODO
                .description("Auth Service")

        val components =
            Components()
                .addSecuritySchemes(AuthenticationScheme.BEARER, authenticationScheme.bearerScheme())

        return OpenAPI()
            .info(info)
            .addServersItem(server)
            .components(components)
            .addSecurityItem(SecurityRequirement().addList(AuthenticationScheme.BEARER))
    }
}