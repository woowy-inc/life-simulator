package ru.woowy.application.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.servlet.http.HttpServletResponseWrapper
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class DeduplicateCorsHeadersFilter : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        val responseWrapper = HeaderDeduplicationResponseWrapper(response)
        filterChain.doFilter(request, responseWrapper)
    }

    private class HeaderDeduplicationResponseWrapper(
        response: HttpServletResponse,
    ) : HttpServletResponseWrapper(response) {
        private val addedHeaders = mutableMapOf<String, MutableSet<String>>()

        private val headersToCheck =
            setOf(
                "Access-Control-Allow-Origin",
                "Access-Control-Allow-Credentials",
                "Access-Control-Expose-Headers",
                "Vary",
            )

        override fun addHeader(
            name: String,
            value: String?,
        ) {
            if (value == null) {
                super.addHeader(name, null)
                return
            }
            if (name in headersToCheck) {
                val existing = addedHeaders.getOrPut(name) { mutableSetOf() }
                if (value in existing) return
                existing.add(value)
            }
            super.addHeader(name, value)
        }

        override fun setHeader(
            name: String,
            value: String?,
        ) {
            if (value == null) {
                super.setHeader(name, null)
                return
            }
            if (name in headersToCheck) {
                addedHeaders[name] = mutableSetOf(value)
            }
            super.setHeader(name, value)
        }
    }
}