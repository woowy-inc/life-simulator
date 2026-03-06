package ru.woowy.infrastructure.lifecycle

import org.springframework.stereotype.Component

@Component
class ServiceScope : CoroutineScopeLifecycle("service")