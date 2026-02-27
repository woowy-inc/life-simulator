package ru.woowy.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.servers.Server
import org.springframework.boot.info.BuildProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.woowy.auth.infrastructure.security.AuthenticationScheme

@Configuration
internal class OpenApiConfig(
    private val authenticationScheme: AuthenticationScheme,
    private val appProperties: AppProperties,
    private val buildProperties: BuildProperties,
) {
    @Bean
    fun openApi(): OpenAPI {
        val info =
            Info()
                .title("Woowy LifeSim: Auth Service")
                .description("Woowy LifeSim: Auth Service description")
                .contact(Contact().name("Denis").email("dnartysh@yandex.ru"))
                .summary("Woowy summary")
                .version(buildProperties.version)

        val server =
            Server()
                .url(appProperties.serverUrl)
                .description("Auth Service")

        val components =
            Components()
                .addSecuritySchemes(AuthenticationScheme.Companion.BEARER, authenticationScheme.bearerScheme())

        return OpenAPI()
            .info(info)
            .addServersItem(server)
            .components(components)
            .addSecurityItem(SecurityRequirement().addList(AuthenticationScheme.Companion.BEARER))
    }
}