package ru.woowy.common.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.woowy.infrastructure.config.ResourceServerSecurityConfiguration

@Configuration
@Import(ResourceServerSecurityConfiguration::class)
class SecurityConfig