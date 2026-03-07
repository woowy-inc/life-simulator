package ru.woowy.infrastructure.lifecycle

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import org.slf4j.LoggerFactory
import org.springframework.context.SmartLifecycle
import kotlin.coroutines.CoroutineContext

abstract class CoroutineScopeLifecycle(
    private val name: String,
) : SmartLifecycle,
    CoroutineScope {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private var scope = createScope()

    override val coroutineContext: CoroutineContext
        get() = scope.coroutineContext

    override fun start() {
        if (!scope.isActive) {
            scope = createScope()
        } else {
            logger.warn("Scope[$name] is already active")
        }
    }

    override fun stop() = scope.cancel()

    override fun isRunning(): Boolean = scope.isActive

    private fun createScope() = CoroutineScope(Dispatchers.Default + SupervisorJob() + CoroutineName(name))
}