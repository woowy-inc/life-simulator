package ru.woowy.application.config

import io.mockk.every
import io.mockk.mockk
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import kotlin.test.assertEquals

internal class DeduplicateCorsHeadersFilterTest {
    private val request = mockk<HttpServletRequest>(relaxed = true)
    private val filterChain = mockk<FilterChain>(relaxed = true)
    private val filter = DeduplicateCorsHeadersFilter()

    @Test
    fun `should not duplicate cors headers`() {
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        every { filterChain.doFilter(any(), any()) } answers {
            val wrapper = secondArg<HttpServletResponse>()
            wrapper.addHeader("Access-Control-Allow-Origin", "https://example.com")
            wrapper.addHeader("Access-Control-Allow-Origin", "https://example.com")
        }

        filter.doFilter(request, response, filterChain)

        assertEquals(1, response.getHeaders("Access-Control-Allow-Origin").size)
    }

    @Test
    fun `should allow different values for same header`() {
        val response = MockHttpServletResponse()

        every { filterChain.doFilter(any(), any()) } answers {
            val wrapper = secondArg<HttpServletResponse>()
            wrapper.addHeader("Vary", "Origin")
            wrapper.addHeader("Vary", "Accept-Encoding")
        }

        filter.doFilter(request, response, filterChain)

        assertEquals(2, response.getHeaders("Vary").size)
    }

    @Test
    fun `should not deduplicate non cors headers`() {
        val response = MockHttpServletResponse()

        every { filterChain.doFilter(any(), any()) } answers {
            val wrapper = secondArg<HttpServletResponse>()
            wrapper.addHeader("X-Custom-Header", "value")
            wrapper.addHeader("X-Custom-Header", "value")
        }

        filter.doFilter(request, response, filterChain)

        assertEquals(2, response.getHeaders("X-Custom-Header").size)
    }
}