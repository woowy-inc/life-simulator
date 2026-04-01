package ru.woowy.infrastructure.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Component

@Component
class ServiceScope(
    name: String = "service-worker",
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : CoroutineScopeLifecycle(name, dispatcher)