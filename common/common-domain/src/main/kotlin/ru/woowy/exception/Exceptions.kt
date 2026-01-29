package ru.woowy.exception

open class LifeSimulatorException(
    cause: String = "",
) : RuntimeException(cause)

open class NotFoundException(
    cause: String = "",
) : LifeSimulatorException(cause)

open class BadRequestException(
    cause: String = "",
) : LifeSimulatorException(cause)

open class ForbiddenException(
    cause: String = "",
) : LifeSimulatorException(cause)

open class UnauthorizedException(
    cause: String = "",
) : LifeSimulatorException(cause)