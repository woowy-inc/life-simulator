package ru.woowy.infrastructure.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer
import ru.woowy.domain.model.Need
import tools.jackson.databind.ObjectMapper

@Configuration
class RedisConfig {
    @Bean
    fun needRedisTemplate(
        connectionFactory: LettuceConnectionFactory,
        objectMapper: ObjectMapper,
    ): RedisTemplate<String, Need> = RedisTemplate<String, Need>().apply {
        setConnectionFactory(connectionFactory)
        keySerializer = StringRedisSerializer()
        valueSerializer = JacksonJsonRedisSerializer(objectMapper, Need::class.java)
    }
}