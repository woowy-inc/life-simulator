package ru.woowy.character.infrastructure.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.stereotype.Component
import ru.woowy.infrastructure.lifecycle.CoroutineScopeLifecycle

@Component
class ServiceScope(
    name: String = "service-worker",
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : CoroutineScopeLifecycle(name, dispatcher)