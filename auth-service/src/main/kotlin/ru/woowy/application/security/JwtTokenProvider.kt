package ru.woowy.application.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.woowy.application.config.JwtProperties
import ru.woowy.security.User
import java.util.Date

@Component
internal class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
) {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)
    private val signingKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())

    fun generateToken(user: User): String {
        val expiresIn = Date(System.currentTimeMillis() + jwtProperties.expiration)

        return Jwts
            .builder()
            .subject(user.username)
            .claim("email", user.username)
            .claim("role", user.role.name)
            .issuedAt(expiresIn)
            .signWith(signingKey)
            .compact()
    }

    fun isTokenValid(token: String): Boolean = try {
        parseClaims(token)
        true
    } catch (ex: JwtException) {
        logger.error(ex.message, ex)
        false
    }

    fun extractUsername(token: String): String = parseClaims(token).subject

    private fun parseClaims(token: String): Claims = Jwts
        .parser()
        .verifyWith(signingKey)
        .build()
        .parseSignedClaims(token)
        .payload
}