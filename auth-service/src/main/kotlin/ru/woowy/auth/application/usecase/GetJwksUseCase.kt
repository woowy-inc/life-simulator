package ru.woowy.auth.application.usecase

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.woowy.common.cache.CacheName
import ru.woowy.common.config.AppProperties
import java.security.interfaces.RSAPublicKey

@Service
internal class GetJwksUseCase(
    private val appProperties: AppProperties,
    private val rsaPublicKey: RSAPublicKey,
) {
    @Cacheable(cacheNames = [CacheName.JWKS])
    operator fun invoke(): Map<String, Any> {
        val jwk =
            RSAKey
                .Builder(rsaPublicKey)
                .keyID(appProperties.jwt.keyId)
                .algorithm(JWSAlgorithm.RS256)
                .keyUse(KeyUse.SIGNATURE)
                .build()

        return mapOf("keys" to listOf(jwk.toJSONObject()))
    }
}