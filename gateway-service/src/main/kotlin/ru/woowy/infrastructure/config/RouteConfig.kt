package ru.woowy.infrastructure.config

import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.woowy.security.Service

@Configuration
class RouteConfig {
    @Bean
    fun routes(builder: RouteLocatorBuilder): RouteLocator = builder
        .routes()
        .route("auth-jwks") { predicate ->
            predicate
                .path("/.well-known/**")
                .uri("lb://${Service.AUTH_SERVICE}")
        }.route("auth-route") { predicate ->
            predicate
                .path("/user/**")
                .uri("lb://${Service.AUTH_SERVICE}")
        }.route("character-route") { predicate ->
            predicate
                .path("/character/**")
                .uri("lb://${Service.CHARACTER_SERVICE}")
        }.route("world-route") { predicate ->
            predicate
                .path("/world/**")
                .uri("lb://${Service.WORLD_SERVICE}")
        }.route("engine-route") { predicate ->
            predicate
                .path("/session/**")
                .uri("lb://${Service.ENGINE_SERVICE}")
        }.route("engine-ws-route") { predicate ->
            predicate
                .path("/ws/engine/**")
                .uri("lb://${Service.ENGINE_SERVICE}")
        }.build()
}