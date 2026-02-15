package ru.woowy.application.config.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.serializer
import ru.woowy.domain.model.Event
import org.apache.kafka.common.serialization.Deserializer as KafkaDeserializer

@OptIn(ExperimentalSerializationApi::class)
class EventDeserializer : KafkaDeserializer<Event> {
    override fun deserialize(
        topic: String?,
        data: ByteArray?,
    ): Event? = data?.let {
        protoBuf.decodeFromByteArray(serializer(), it)
    }
}