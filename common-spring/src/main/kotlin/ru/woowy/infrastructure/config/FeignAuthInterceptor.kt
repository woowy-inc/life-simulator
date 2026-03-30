package ru.woowy.infrastructure.config

import feign.RequestInterceptor
import feign.RequestTemplate
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

@Component
class FeignAuthInterceptor(
    private val request: HttpServletRequest,
) : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val token = request.getHeader(HttpHeaders.AUTHORIZATION)

        if (token != null) {
            template.header(HttpHeaders.AUTHORIZATION, token)
        }
    }
}