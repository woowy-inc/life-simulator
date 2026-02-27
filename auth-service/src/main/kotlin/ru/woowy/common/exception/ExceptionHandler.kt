package ru.woowy.common.exception

import jakarta.validation.ConstraintViolationException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import ru.woowy.exception.BadRequestException
import ru.woowy.exception.ExceptionResponse
import ru.woowy.exception.ForbiddenException
import ru.woowy.exception.InternalException
import ru.woowy.exception.NotFoundException
import ru.woowy.exception.UnauthorizedException

@RestControllerAdvice
class ExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException): ResponseEntity<ExceptionResponse> =
        ResponseEntity.status(HttpStatus.NOT_FOUND).body(ExceptionResponse(ex.message.orEmpty()))

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException): ResponseEntity<ExceptionResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ExceptionResponse(ex.message.orEmpty()))

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<ExceptionResponse> =
        ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ExceptionResponse(ex.message.orEmpty()))

    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(ex: ForbiddenException): ResponseEntity<ExceptionResponse> =
        ResponseEntity.status(HttpStatus.FORBIDDEN).body(ExceptionResponse(ex.message.orEmpty()))

    @ExceptionHandler(InternalException::class)
    fun handleInternalException(ex: InternalException): ResponseEntity<ExceptionResponse> {
        logger.error("An unexpected error occurred", ex)
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationErrors(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionResponse> {
        val errors =
            ex.bindingResult
                ?.fieldErrors
                ?.associate { it.field to (it.defaultMessage ?: "Invalid value") } ?: emptyMap()

        return ResponseEntity
            .badRequest()
            .body(ExceptionResponse(error = "Validation error", details = errors))
    }

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolation(ex: ConstraintViolationException): ResponseEntity<ExceptionResponse> {
        val errors =
            ex.constraintViolations
                .associate { it.propertyPath.toString() to it.message }

        return ResponseEntity
            .badRequest()
            .body(ExceptionResponse(error = "Validation error", details = errors))
    }
}