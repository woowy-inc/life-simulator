package ru.woowy.infrastructure.messaging.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import ru.woowy.game.KafkaTopic

@Configuration
@EnableKafka
class KafkaConfig {
    @Bean
    fun worldTickTopic(): NewTopic = NewTopic(KafkaTopic.WORLD_TICK.title, 5, 1.toShort())
}