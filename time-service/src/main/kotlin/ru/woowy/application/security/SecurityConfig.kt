package ru.woowy.application.security

import org.springframework.boot.security.oauth2.server.resource.autoconfigure.OAuth2ResourceServerProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.JwtIssuerValidator
import org.springframework.security.oauth2.jwt.JwtTimestampValidator
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import org.springframework.security.web.SecurityFilterChain
import ru.woowy.application.config.AppProperties

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
internal class SecurityConfig(
    private val appProperties: AppProperties,
    private val oauth2Properties: OAuth2ResourceServerProperties,
) {
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain = http
        .csrf { it.disable() }
        .authorizeHttpRequests { auth ->
            auth
                .requestMatchers("/actuator/health")
                .permitAll()
                .anyRequest()
                .authenticated()
        }.oauth2ResourceServer { oauth2 ->
            oauth2.jwt { jwt ->
                jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())
            }
        }.sessionManagement {
            it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        }.build()

    @Bean
    fun jwtDecoder(): JwtDecoder = NimbusJwtDecoder
        .withJwkSetUri(oauth2Properties.jwt.jwkSetUri)
        .build()
        .apply {
            setJwtValidator(
                DelegatingOAuth2TokenValidator(
                    JwtTimestampValidator(),
                    JwtIssuerValidator(appProperties.jwt.issuer),
                ),
            )
        }

    @Bean
    fun jwtAuthenticationConverter(): JwtAuthenticationConverter {
        val converter = JwtAuthenticationConverter()
        converter.setJwtGrantedAuthoritiesConverter { jwt ->
            val role = jwt.getClaimAsString("role")
            if (role != null) {
                listOf(SimpleGrantedAuthority("ROLE_$role"))
            } else {
                emptyList()
            }
        }
        return converter
    }
}