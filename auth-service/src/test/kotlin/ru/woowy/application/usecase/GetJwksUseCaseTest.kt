package ru.woowy.application.usecase

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import ru.woowy.application.config.app.AppProperties
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPublicKey

class GetJwksUseCaseTest {
    private val rsaPublicKey =
        KeyPairGenerator
            .getInstance("RSA")
            .apply { initialize(2048) }
            .generateKeyPair()
            .public as RSAPublicKey

    private val appProperties = mockk<AppProperties>(relaxed = true)
    private val useCase = GetJwksUseCase(appProperties, rsaPublicKey)

    @Test
    fun `should return map of jwks`() {
        val result = useCase()

        val keys = result["keys"] as List<*>
        assertEquals(1, keys.size)
        assertNotNull(keys[0])
    }
}