package ru.woowy.character.infrastructure.lifecycle

import org.springframework.stereotype.Component
import ru.woowy.infrastructure.lifecycle.CoroutineScopeLifecycle

@Component
class ServiceScope : CoroutineScopeLifecycle("service-worker")