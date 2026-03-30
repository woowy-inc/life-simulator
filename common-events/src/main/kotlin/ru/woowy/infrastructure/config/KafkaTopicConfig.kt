package ru.woowy.infrastructure.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import ru.woowy.messaging.KafkaTopic

@Configuration(proxyBeanMethods = false)
class KafkaTopicConfig {
    @Bean
    fun userEventsTopic() = NewTopic(KafkaTopic.USER_EVENTS, 5, 1.toShort())

    @Bean
    fun characterEventsTopic() = NewTopic(KafkaTopic.CHARACTER_EVENTS, 5, 1.toShort())

    @Bean
    fun worldEventsTopic() = NewTopic(KafkaTopic.WORLD_EVENTS, 5, 1.toShort())

    @Bean
    fun worldTickEventsTopic() = NewTopic(KafkaTopic.WORLD_TICK_EVENTS, 5, 1.toShort())

    @Bean
    fun characterStateEventsTopic() = NewTopic(KafkaTopic.CHARACTER_STATE_EVENTS, 5, 1.toShort())
}