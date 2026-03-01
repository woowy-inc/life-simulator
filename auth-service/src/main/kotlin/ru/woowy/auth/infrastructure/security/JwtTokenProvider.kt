package ru.woowy.auth.infrastructure.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.woowy.auth.domain.model.Token
import ru.woowy.auth.domain.model.TokenType
import ru.woowy.common.config.AppProperties
import ru.woowy.security.TokenClaim
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

    fun generateAccessToken(user: User) = generateToken(user, TokenType.ACCESS, appProperties.jwt.expiration)

    fun generateRefreshToken(user: User): Token =
        generateToken(user, TokenType.REFRESH, appProperties.jwt.refreshExpiration)

    private fun generateToken(
        user: User,
        tokenType: TokenType,
        expiration: Long,
    ): Token {
        val now = Instant.now()
        val expiresAt = now.plusMillis(expiration)

        val token =
            Jwts
                .builder()
                .header()
                .keyId(appProperties.jwt.keyId)
                .and()
                .subject(user.id.toString())
                .claim(TokenClaim.USERNAME, user.username)
                .claim(TokenClaim.ROLE, user.role.name)
                .claim(TokenClaim.TOKEN_TYPE, tokenType)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiresAt))
                .issuer(appProperties.jwt.issuer)
                .signWith(rsaPrivateKey, Jwts.SIG.RS256)
                .compact()

        return Token(token, expiration)
    }

    fun isTokenValid(token: String): Boolean = try {
        parseClaims(token)
        true
    } catch (ex: JwtException) {
        logger.error("JWT validation failed: ${ex.message}", ex)
        false
    }

    fun extractUsername(token: String): String = parseClaims(token).subject

    fun extractTokenType(token: String): TokenType {
        val stringType = parseClaims(token)[TokenClaim.TOKEN_TYPE] as String

        return TokenType.valueOf(stringType)
    }

    fun extractRole(token: String): String = parseClaims(token)["role"] as String

    private fun parseClaims(token: String): Claims = Jwts
        .parser()
        .verifyWith(rsaPublicKey)
        .build()
        .parseSignedClaims(token)
        .payload
}