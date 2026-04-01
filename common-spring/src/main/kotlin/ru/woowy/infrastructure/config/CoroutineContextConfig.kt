package ru.woowy.infrastructure.config

import java.lang.reflect.Method
import kotlinx.coroutines.Dispatchers
import org.springframework.boot.webmvc.autoconfigure.WebMvcRegistrations
import org.springframework.context.annotation.Configuration
import org.springframework.core.CoroutinesUtils
import org.springframework.web.method.HandlerMethod
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod

@Configuration
class CoroutineContextConfig : WebMvcRegistrations {
    override fun getRequestMappingHandlerAdapter(): RequestMappingHandlerAdapter? =
        CoroutineContextAwareMappingHandler()
}

class CoroutineContextAwareMappingHandler : RequestMappingHandlerAdapter() {
    override fun createInvocableHandlerMethod(handlerMethod: HandlerMethod) =
        CoroutineContextAwareInvocableMethod(handlerMethod)
}

class CoroutineContextAwareInvocableMethod(
    handlerMethod: HandlerMethod,
) : ServletInvocableHandlerMethod(handlerMethod) {
    @Suppress("ReactiveStreamsUnusedPublisher")
    override fun invokeSuspendingFunction(
        method: Method,
        target: Any,
        args: Array<out Any?>,
    ): Any = CoroutinesUtils.invokeSuspendingFunction(Dispatchers.IO, method, target, *args)
}