package ru.woowy.domain.model

import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class UserPrincipalAuthentication(
    val userPrincipal: UserPrincipal,
    token: Jwt,
) : JwtAuthenticationToken(token, userPrincipal.authorities, userPrincipal.username) {
    override fun getPrincipal(): UserPrincipal = userPrincipal
}