package ru.woowy.application.service

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import ru.woowy.domain.service.ApiDocumentationProxyService
import ru.woowy.extension.internalError
import ru.woowy.security.ServiceId

@Service
class ApiDocumentationProxyServiceImpl(
    private val webClientBuilder: WebClient.Builder,
) : ApiDocumentationProxyService {
    override suspend fun get(serviceId: ServiceId): String = webClientBuilder
        .build()
        .get()
        .uri("lb://$serviceId/v3/api-docs")
        .retrieve()
        .bodyToMono<String>()
        .awaitSingle()
        ?: internalError("Empty response from $serviceId")
}