package ru.woowy.infrastructure.lifecycle

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.isActive
import org.springframework.context.SmartLifecycle
import ru.woowy.extension.classLogger
import kotlin.coroutines.CoroutineContext

abstract class CoroutineScopeLifecycle(
    private val name: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
) : SmartLifecycle,
    CoroutineScope {
    private val logger = classLogger()
    private var scope = createScope()

    override val coroutineContext: CoroutineContext
        get() = scope.coroutineContext

    override fun start() {
        if (!scope.isActive) {
            scope = createScope()
        } else {
            logger.warn("Lifecycle scope[$name] is already started...")
        }
    }

    override fun stop() = scope.cancel().also { logger.info("Lifecycle scope[$name] successfully stopped...") }

    override fun isRunning(): Boolean = scope.isActive

    private fun createScope() = CoroutineScope(dispatcher + SupervisorJob() + CoroutineName(name)).also {
        logger.info("Lifecycle scope[$name] successfully created...")
    }
}