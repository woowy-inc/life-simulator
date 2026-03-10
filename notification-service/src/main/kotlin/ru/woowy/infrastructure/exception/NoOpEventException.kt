package ru.woowy.infrastructure.exception

import ru.woowy.exception.LifeSimulatorException

class NoOpEventException(
    override val message: String = "",
) : LifeSimulatorException(message)