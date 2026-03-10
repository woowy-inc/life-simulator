package ru.woowy.application

import org.junit.jupiter.api.Test
import ru.woowy.domain.model.CharacterCreatedEvent
import ru.woowy.domain.model.CharacterDeletedEvent
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.domain.model.WorldCreatedEvent
import ru.woowy.infrastructure.serializer.EventDeserializer
import ru.woowy.infrastructure.serializer.EventSerializer
import ru.woowy.util.randomEmail
import ru.woowy.util.randomGender
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID
import ru.woowy.util.randomUserRole
import ru.woowy.util.randomUsername
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EventSerializerTest {
    private val serializer = EventSerializer()
    private val deserializer = EventDeserializer()

    @Test
    fun `should serialize and deserialize UserRegisteredEvent`() {
        val event =
            UserRegisteredEvent(
                eventId = randomUUID(),
                timestamp = System.currentTimeMillis(),
                userId = randomUUID(),
                username = randomUsername(),
                email = randomEmail(),
                firstName = randomString(),
                role = randomUserRole(),
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should serialize and deserialize UserRegisterRequestedEvent`() {
        val event =
            UserRegisterRequestedEvent(
                eventId = randomUUID(),
                timestamp = System.currentTimeMillis(),
                userId = randomUUID(),
                username = randomUsername(),
                email = randomEmail(),
                firstName = randomString(),
                key = randomUUID().toString(),
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should serialize and deserialize CharacterCreatedEvent`() {
        val event =
            CharacterCreatedEvent(
                eventId = randomUUID(),
                timestamp = System.currentTimeMillis(),
                userId = randomUUID(),
                characterId = randomUUID(),
                gender = randomGender(),
                locationId = randomUUID(),
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should serialize and deserialize CharacterDeletedEvent`() {
        val event =
            CharacterDeletedEvent(
                eventId = randomUUID(),
                timestamp = System.currentTimeMillis(),
                userId = randomUUID(),
                characterId = randomUUID(),
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should serialize and deserialize WorldCreatedEvent`() {
        val event =
            WorldCreatedEvent(
                eventId = randomUUID(),
                timestamp = System.currentTimeMillis(),
                characterId = randomUUID(),
                worldId = randomUUID(),
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should return null when serialize null`() {
        assertNull(serializer.serialize("topic", null))
    }

    @Test
    fun `should return null when deserialize null`() {
        assertNull(deserializer.deserialize("topic", null))
    }
}