package ru.woowy.infrastructure.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import ru.woowy.messaging.KafkaTopic

@Configuration
@EnableKafka
class KafkaConfig {
    @Bean
    fun worldTickEventsTopic(): NewTopic = NewTopic(KafkaTopic.WORLD_TICK_EVENTS, 5, 1.toShort())
}