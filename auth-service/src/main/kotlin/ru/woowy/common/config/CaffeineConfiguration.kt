package ru.woowy.common.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@EnableCaching
class CaffeineConfiguration {
    @Bean
    fun cacheManager(): CaffeineCacheManager = CaffeineCacheManager().apply {
        setCaffeine(
            Caffeine
                .newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(Duration.ofMinutes(10))
                .recordStats(),
        )
    }
}