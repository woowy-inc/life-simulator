package ru.woowy.infrastructure.serializer

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlinx.serialization.protobuf.ProtoBuf
import ru.woowy.domain.model.CharacterCreatedEvent
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.Event
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.model.WorldCreatedEvent
import java.util.UUID

private val eventModule =
    SerializersModule {
        contextual(UUID::class, UUIDSerializer)

        polymorphic(Event::class) {
            subclass(UserRegisterRequestedEvent::class)
            subclass(UserRegisteredEvent::class)
            subclass(CharacterCreatedEvent::class)
            subclass(CharacterDeletedEvent::class)
            subclass(WorldCreatedEvent::class)
        }
    }

@OptIn(ExperimentalSerializationApi::class)
val protoBuf = ProtoBuf { serializersModule = eventModule }