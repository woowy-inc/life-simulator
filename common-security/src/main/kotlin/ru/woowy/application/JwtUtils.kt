package ru.woowy.application

import org.springframework.context.annotation.Bean
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter
import ru.woowy.domain.model.UserPrincipal
import ru.woowy.security.TokenClaim
import ru.woowy.security.UserRole
import java.util.UUID

fun Jwt.toUserPrincipal() = UserPrincipal(
    userId = UUID.fromString(subject),
    login = getClaimAsString(TokenClaim.USERNAME),
    role = UserRole.valueOf(getClaimAsString(TokenClaim.ROLE)),
)

@Bean
fun jwtAuthenticationConverter(): JwtAuthenticationConverter = JwtAuthenticationConverter().apply {
    setJwtGrantedAuthoritiesConverter { jwt ->
        val principal = jwt.toUserPrincipal()
        listOf(SimpleGrantedAuthority(principal.role.name))
    }
    setPrincipalClaimName("username")
}