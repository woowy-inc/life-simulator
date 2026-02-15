package ru.woowy.application.config.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer
import ru.woowy.domain.model.Event
import org.apache.kafka.common.serialization.Serializer as KafkaSerializer

@OptIn(ExperimentalSerializationApi::class)
class EventSerializer : KafkaSerializer<Event> {
    override fun serialize(
        topic: String?,
        data: Event?,
    ): ByteArray? = data?.let {
        protoBuf.encodeToByteArray(serializer(), it)
    }
}