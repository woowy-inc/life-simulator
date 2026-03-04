package ru.woowy.domain.service

import ru.woowy.security.ServiceId

interface ApiDocumentationProxyService {
    fun get(serviceId: ServiceId): String
}