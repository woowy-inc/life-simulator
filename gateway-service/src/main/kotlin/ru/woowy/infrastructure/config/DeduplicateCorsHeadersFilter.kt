package ru.woowy.infrastructure.config

import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.GlobalFilter
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
@Order(Ordered.LOWEST_PRECEDENCE)
class DeduplicateCorsHeadersFilter : GlobalFilter {
    private val headersToCheck =
        setOf(
            "Access-Control-Allow-Origin",
            "Access-Control-Allow-Credentials",
            "Access-Control-Expose-Headers",
            "Vary",
        )

    override fun filter(
        exchange: ServerWebExchange,
        chain: GatewayFilterChain,
    ): Mono<Void> {
        return chain.filter(exchange).then(
            Mono.fromRunnable {
                val response = exchange.response
                headersToCheck.forEach { header ->
                    val values = response.headers[header] ?: return@forEach
                    val unique = values.distinct()
                    response.headers.remove(header)
                    response.headers.addAll(header, unique)
                }
            },
        )
    }
}