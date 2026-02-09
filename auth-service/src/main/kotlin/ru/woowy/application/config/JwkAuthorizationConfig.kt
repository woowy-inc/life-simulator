package ru.woowy.application.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.vault.core.VaultKeyValueOperationsSupport
import org.springframework.vault.core.VaultTemplate
import java.security.KeyFactory
import java.security.KeyPair
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.security.spec.PKCS8EncodedKeySpec
import java.security.spec.X509EncodedKeySpec
import java.util.Base64

private const val RSA_ALGORITHM = "RSA"
private const val RSA_PUBLIC_KEY = "jwt.rsa.public"
private const val RSA_PRIVATE_KEY = "jwt.rsa.private"

@Configuration
internal class JwkAuthorizationConfig(
    private val vaultTemplate: VaultTemplate,
    @param:Value("\${spring.cloud.vault.kv.backend}")
    private val backend: String,
    @param:Value("\${spring.cloud.vault.kv.application-name}")
    private val appName: String,
) {
    @Bean
    fun keyPair(): KeyPair {
        val data =
            vaultTemplate
                .opsForKeyValue(backend, VaultKeyValueOperationsSupport.KeyValueBackend.KV_2)
                .get(appName)
                ?.data
                ?: error("No secret '$appName' in '$backend'")

        val publicB64 = data[RSA_PUBLIC_KEY] as? String ?: error("public key missing")
        val privateB64 = data[RSA_PRIVATE_KEY] as? String ?: error("private key missing")

        val publicKey = loadPublicKey(publicB64)
        val privateKey = loadPrivateKey(privateB64)

        return KeyPair(publicKey, privateKey)
    }

    @Bean
    fun rsaPublicKey(keyPair: KeyPair): RSAPublicKey = keyPair.public as RSAPublicKey

    @Bean
    fun rsaPrivateKey(keyPair: KeyPair): RSAPrivateKey = keyPair.private as RSAPrivateKey

    private fun loadPublicKey(key: String): RSAPublicKey {
        val bytes = Base64.getDecoder().decode(key)
        val keySpec = X509EncodedKeySpec(bytes)
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)

        return keyFactory.generatePublic(keySpec) as RSAPublicKey
    }

    private fun loadPrivateKey(key: String): RSAPrivateKey {
        val bytes = Base64.getDecoder().decode(key)
        val keySpec = PKCS8EncodedKeySpec(bytes)
        val keyFactory = KeyFactory.getInstance(RSA_ALGORITHM)

        return keyFactory.generatePrivate(keySpec) as RSAPrivateKey
    }
}