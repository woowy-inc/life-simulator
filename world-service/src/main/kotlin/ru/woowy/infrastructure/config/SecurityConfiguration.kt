package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.woowy.application.config.ResourceServerSecurityConfiguration

@Configuration
@Import(ResourceServerSecurityConfiguration::class)
internal class SecurityConfiguration