package ru.woowy.application.config.messaging

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import ru.woowy.game.KafkaTopic

@Configuration
@EnableKafka
class KafkaConfig {
    @Bean
    fun userEventsTopic(): NewTopic = NewTopic(KafkaTopic.USER_EVENTS, 5, 1.toShort())
}