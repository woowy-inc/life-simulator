package ru.woowy.application.usecase

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.woowy.domain.model.CacheName
import ru.woowy.domain.usecase.JwkUseCase
import ru.woowy.infrastructure.config.AppProperties
import java.security.interfaces.RSAPublicKey

@Service
class JwkUseCaseImpl(
    private val appProperties: AppProperties,
    private val rsaPublicKey: RSAPublicKey,
) : JwkUseCase {
    @Cacheable(cacheNames = [CacheName.JWKS])
    override fun getJwks(): Map<String, Any> {
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