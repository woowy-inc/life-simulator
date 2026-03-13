package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ResourceServerSecurityConfiguration::class)
class SecurityConfig