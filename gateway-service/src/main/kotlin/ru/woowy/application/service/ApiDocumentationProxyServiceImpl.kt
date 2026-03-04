package ru.woowy.application.service

import org.springframework.cloud.client.loadbalancer.LoadBalancerClient
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClient
import org.springframework.web.client.body
import ru.woowy.domain.service.ApiDocumentationProxyService
import ru.woowy.extension.internalError
import ru.woowy.security.ServiceId

@Service
class ApiDocumentationProxyServiceImpl(
    private val loadBalancerClient: LoadBalancerClient,
    private val restClient: RestClient,
) : ApiDocumentationProxyService {
    override fun get(serviceId: ServiceId): String {
        val instance = loadBalancerClient.choose(serviceId)

        val uri = "http://${instance.host}:${instance.port}/v3/api-docs"

        return restClient
            .get()
            .uri(uri)
            .retrieve()
            .body<String>()
            ?: internalError("Empty response from $serviceId")
    }
}