package ru.woowy.application.config

import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import reactor.core.publisher.Mono
import ru.woowy.infrastructure.config.DeduplicateCorsHeadersFilter
import kotlin.test.assertEquals

class DeduplicateCorsHeadersFilterTest {
    private val filter = DeduplicateCorsHeadersFilter()
    private val chain = mockk<GatewayFilterChain>()

    @Test
    fun `should not duplicate cors headers`() {
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build())
        exchange.response.headers.add("Access-Control-Allow-Origin", "https://example.com")
        exchange.response.headers.add("Access-Control-Allow-Origin", "https://example.com")

        every { chain.filter(any()) } returns Mono.empty()

        filter.filter(exchange, chain).block()

        assertEquals(1, exchange.response.headers["Access-Control-Allow-Origin"]?.size)
    }

    @Test
    fun `should allow different values for same header`() {
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build())
        exchange.response.headers.add("Vary", "Origin")
        exchange.response.headers.add("Vary", "Accept-Encoding")

        every { chain.filter(any()) } returns Mono.empty()

        filter.filter(exchange, chain).block()

        assertEquals(2, exchange.response.headers["Vary"]?.size)
    }

    @Test
    fun `should not deduplicate non cors headers`() {
        val exchange = MockServerWebExchange.from(MockServerHttpRequest.get("/").build())
        exchange.response.headers.add("X-Custom-Header", "value")
        exchange.response.headers.add("X-Custom-Header", "value")

        every { chain.filter(any()) } returns Mono.empty()

        filter.filter(exchange, chain).block()

        assertEquals(2, exchange.response.headers["X-Custom-Header"]?.size)
    }
}