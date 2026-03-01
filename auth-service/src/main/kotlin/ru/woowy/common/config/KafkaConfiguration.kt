package ru.woowy.common.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import ru.woowy.messaging.KafkaTopic

@Configuration
@EnableKafka
class KafkaConfiguration {
    @Bean
    fun userEventsTopic(): NewTopic = NewTopic(KafkaTopic.USER_EVENTS, 5, 1.toShort())
}