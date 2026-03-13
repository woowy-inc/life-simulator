package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import ru.woowy.application.UserPrincipalJwtConverter

@EnableWebSecurity
class ResourceServerSecurityConfiguration {
    @Bean
    fun userPrincipalJwtConverter(): UserPrincipalJwtConverter = UserPrincipalJwtConverter()

    @Bean
    fun securityFilterChain(
        http: HttpSecurity,
        jwtAuthenticationConverter: UserPrincipalJwtConverter,
    ): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { it.jwtAuthenticationConverter(jwtAuthenticationConverter) }
            }.authorizeHttpRequests {
                it.requestMatchers("/v3/api-docs/**").permitAll()
                it.anyRequest().authenticated()
            }

        return http.build()
    }
}