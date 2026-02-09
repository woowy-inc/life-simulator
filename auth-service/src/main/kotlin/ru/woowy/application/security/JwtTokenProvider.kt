package ru.woowy.application.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.woowy.application.config.AppProperties
import ru.woowy.security.User
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Instant
import java.util.Date

@Component
internal class JwtTokenProvider(
    private val appProperties: AppProperties,
    private val rsaPrivateKey: RSAPrivateKey,
    private val rsaPublicKey: RSAPublicKey,
) {
    private val logger = LoggerFactory.getLogger(JwtTokenProvider::class.java)

    fun generateToken(user: User): String {
        val now = Instant.now()
        val expiresAt = now.plusMillis(appProperties.jwt.expiration)

        return Jwts
            .builder()
            .header()
            .keyId(appProperties.jwt.keyId)
            .and()
            .subject(user.username)
            .claim("email", user.username)
            .claim("role", user.role.name)
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiresAt))
            .issuer(appProperties.jwt.issuer)
            .signWith(rsaPrivateKey, Jwts.SIG.RS256)
            .compact()
    }

    fun isTokenValid(token: String): Boolean = try {
        parseClaims(token)
        true
    } catch (ex: JwtException) {
        logger.error("JWT validation failed: ${ex.message}", ex)
        false
    }

    fun extractUsername(token: String): String = parseClaims(token).subject

    fun extractRole(token: String): String = parseClaims(token)["role"] as String

    private fun parseClaims(token: String): Claims = Jwts
        .parser()
        .verifyWith(rsaPublicKey)
        .build()
        .parseSignedClaims(token)
        .payload
}