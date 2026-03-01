package ru.woowy.application

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.oauth2.jwt.Jwt
import ru.woowy.domain.model.UserPrincipalAuthentication

class UserPrincipalJwtConverter : Converter<Jwt, AbstractAuthenticationToken> {
    override fun convert(jwt: Jwt) = UserPrincipalAuthentication(
        userPrincipal = jwt.toUserPrincipal(),
        token = jwt,
    )
}