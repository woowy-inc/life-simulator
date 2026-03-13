package ru.woowy.infrastructure.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.woowy.infrastructure.security.AuthenticationScheme

@Configuration
class OpenApiConfig(
    private val authenticationScheme: AuthenticationScheme,
    private val appProperties: AppProperties,
    private val buildProperties: BuildProperties,
) {
    @Bean
    fun openApi(): OpenAPI {
        val info =
            Info()
                .title("LifeSim Character Service")
                .description("Character service for creating and managing characters")
                .contact(Contact().name("Denis").email("dnartysh@yandex.ru"))
                .version(buildProperties.version)

        val server =
            Server()
                .url(appProperties.gatewayUrl)
                .description("Gateway")

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