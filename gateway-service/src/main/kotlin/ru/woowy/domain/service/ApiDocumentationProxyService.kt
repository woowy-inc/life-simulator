package ru.woowy.domain.service

import ru.woowy.security.ServiceId

interface ApiDocumentationProxyService {
    suspend fun get(serviceId: ServiceId): String
}