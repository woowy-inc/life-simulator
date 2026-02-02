package ru.woowy.application.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey

@Configuration
class JwkAuthorizationConfig {
    @Bean
    fun keyPair(): KeyPair {
        // TODO add vault to work with rsa keys
        val generator =
            KeyPairGenerator.getInstance("RSA").apply {
                initialize(2048)
            }

        return generator.generateKeyPair()
    }

    @Bean
    fun rsaPublicKey(keyPair: KeyPair): RSAPublicKey = keyPair.public as RSAPublicKey

    @Bean
    fun rsaPrivateKey(keyPair: KeyPair): RSAPrivateKey = keyPair.private as RSAPrivateKey
}