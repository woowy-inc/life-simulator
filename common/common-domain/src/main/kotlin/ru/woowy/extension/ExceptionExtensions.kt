package ru.woowy.extension

import ru.woowy.exception.BadRequestException
import ru.woowy.exception.ForbiddenException
import ru.woowy.exception.InternalException
import ru.woowy.exception.NotFoundException
import ru.woowy.exception.UnauthorizedException

fun notFound(cause: String = ""): Nothing = throw NotFoundException(cause)

fun badRequest(cause: String = ""): Nothing = throw BadRequestException(cause)

fun forbidden(cause: String = ""): Nothing = throw ForbiddenException(cause)

fun unauthorized(cause: String = ""): Nothing = throw UnauthorizedException(cause)

fun internalError(cause: String = ""): Nothing = throw InternalException(cause)