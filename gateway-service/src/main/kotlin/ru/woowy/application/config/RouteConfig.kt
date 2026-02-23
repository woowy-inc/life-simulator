package ru.woowy.application.config

import org.springframework.cloud.gateway.server.mvc.filter.LoadBalancerFilterFunctions.lb
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http
import org.springframework.cloud.gateway.server.mvc.predicate.GatewayRequestPredicates.path
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.function.RouterFunction
import org.springframework.web.servlet.function.ServerResponse
import ru.woowy.security.Service

@Configuration
internal class RouteConfig {
    @Bean
    fun gatewayRoutes(): RouterFunction<ServerResponse> = route("auth-jwks")
        .GET("/.well-known/**", http())
        .filter(lb(Service.AUTH_SERVICE.id))
        .build()
        .and(
            route("auth-route")
                .route(path("/user/**"), http())
                .filter(lb(Service.AUTH_SERVICE.id))
                .build(),
        ).and(
            route("time-route")
                .route(path("/time/**"), http())
                .filter(lb(Service.TIME_SERVICE.id))
                .build(),
        ).and(
            route("character-route")
                .route(path("/character/**"), http())
                .filter(lb(Service.CHARACTER_SERVICE.id))
                .build(),
        ).and(
            route("world-route")
                .route(path("/world/**"), http())
                .filter(lb(Service.WORLD_SERVICE.id))
                .build(),
        )
}