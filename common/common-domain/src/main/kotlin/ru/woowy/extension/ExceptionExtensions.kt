package ru.woowy.extension

import ru.woowy.exception.BadRequestException
import ru.woowy.exception.ForbiddenException
import ru.woowy.exception.NotFoundException

fun notFound(cause: String = ""): Nothing = throw NotFoundException(cause)

fun badRequest(cause: String = ""): Nothing = throw BadRequestException(cause)

fun forbidden(cause: String = ""): Nothing = throw ForbiddenException(cause)