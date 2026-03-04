package ru.woowy.application.service

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.web.client.RestClient
import org.springframework.web.client.body

class ApiDocumentationProxyServiceTest {
    private val loadBalancerClient = mockk<LoadBalancerClient>()
    private val restClient = mockk<RestClient>(relaxed = true)
    private val service = ApiDocumentationProxyServiceImpl(loadBalancerClient, restClient)

    private val serviceId = "auth-service"
    private val instance = mockk<ServiceInstance>()

    @Test
    fun `should return api docs`() {
        val expected = """{"openapi": "3.0.0"}"""
        val requestSpec = mockk<RestClient.RequestHeadersUriSpec<*>>(relaxed = true)
        val responseSpec = mockk<RestClient.ResponseSpec>(relaxed = true)

        every { loadBalancerClient.choose(serviceId) } returns instance
        every { instance.host } returns "localhost"
        every { instance.port } returns 8080
        every { restClient.get() } returns requestSpec
        every { requestSpec.uri(any<String>()) } returns requestSpec
        every { requestSpec.retrieve() } returns responseSpec
        every { responseSpec.body<String>() } returns expected

        val actual = service.get(serviceId)

        assertEquals(expected, actual)
    }
}