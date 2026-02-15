package ru.woowy.application.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.protobuf.ProtoBuf
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisteredEvent

private val eventModule =
    SerializersModule {
        polymorphic(Event::class) {
            subclass(UserRegisteredEvent::class)
        }
    }

@OptIn(ExperimentalSerializationApi::class)
val protoBuf = ProtoBuf { serializersModule = eventModule }