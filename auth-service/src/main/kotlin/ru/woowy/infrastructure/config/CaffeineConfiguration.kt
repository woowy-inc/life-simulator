package ru.woowy.infrastructure.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.cache.support.NoOpCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
@EnableCaching
class CaffeineConfiguration(
    private val appProperties: AppProperties,
) {
    @Bean
    fun cacheManager(): CacheManager {
        if (!appProperties.cacheEnabled) return NoOpCacheManager()

        return CaffeineCacheManager().apply {
            setCaffeine(
                Caffeine
                    .newBuilder()
                    .maximumSize(1000)
                    .expireAfterWrite(Duration.ofMinutes(15))
                    .recordStats(),
            )
        }
    }
}