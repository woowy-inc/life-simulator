package ru.woowy.infrastructure.web.controller

import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import ru.woowy.domain.service.ApiDocumentationProxyService
import ru.woowy.security.ServiceId

@RestController
internal class ApiDocsProxyController(
    private val apiDocumentationProxyService: ApiDocumentationProxyService,
) {
    @GetMapping("/api-docs/{serviceId}")
    fun getServiceDocumentation(
        @PathVariable serviceId: ServiceId,
    ): ResponseEntity<String> = ResponseEntity
        .ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(apiDocumentationProxyService.get(serviceId))
}