package ru.woowy.domain.usecase

import ru.woowy.domain.model.Event

interface EmailUseCase {
    fun send(event: Event)
}