package ru.woowy.application.config.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.protobuf.ProtoBuf
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent

@OptIn(ExperimentalSerializationApi::class)
val protoBuf = ProtoBuf { serializersModule = eventModule }

private val eventModule =
    SerializersModule {
        polymorphic(Event::class) {
            subclass(UserRegisteredEvent::class)
        }
    }