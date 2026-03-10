package ru.woowy.infrastructure.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.PolymorphicSerializer
import ru.woowy.domain.model.Event
import org.apache.kafka.common.serialization.Deserializer as KafkaDeserializer

@OptIn(ExperimentalSerializationApi::class)
class EventDeserializer : KafkaDeserializer<Event> {
    override fun deserialize(
        topic: String?,
        data: ByteArray?,
    ): Event? = data?.let {
        protoBuf.decodeFromByteArray(PolymorphicSerializer(Event::class), it)
    }
}