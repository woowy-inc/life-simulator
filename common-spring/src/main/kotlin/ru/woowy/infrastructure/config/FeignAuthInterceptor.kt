package ru.woowy.infrastructure.config

import feign.RequestInterceptor
import feign.RequestTemplate
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component

val authTokenHolder = InheritableThreadLocal<String?>()

@Component
class FeignAuthInterceptor : RequestInterceptor {
    override fun apply(template: RequestTemplate) {
        val token = authTokenHolder.get()
        if (token != null) {
            template.header(HttpHeaders.AUTHORIZATION, token)
        }
    }
}