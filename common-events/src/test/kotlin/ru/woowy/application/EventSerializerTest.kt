package ru.woowy.application

import org.junit.jupiter.api.Test
import ru.woowy.application.serializer.EventDeserializer
import ru.woowy.application.serializer.EventSerializer
import ru.woowy.domain.model.UserRegisterRequestedEvent
import ru.woowy.domain.model.UserRegisteredEvent
import ru.woowy.security.UserRole
import java.util.UUID
import kotlin.test.assertEquals
import kotlin.test.assertNull

class EventSerializerTest {
    private val serializer = EventSerializer()
    private val deserializer = EventDeserializer()

    @Test
    fun `should serialize and deserialize UserRegisteredEvent`() {
        val event =
            UserRegisteredEvent(
                eventId = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                userId = UUID.randomUUID().toString(),
                username = "test",
                email = "test@test.com",
                firstName = "Test",
                role = UserRole.USER,
            )

        val bytes = serializer.serialize("topic", event)
        val result = deserializer.deserialize("topic", bytes)

        assertEquals(event, result)
    }

    @Test
    fun `should serialize and deserialize UserRegisterRequestedEvent`() {
        val event =
            UserRegisterRequestedEvent(
                eventId = UUID.randomUUID().toString(),
                timestamp = System.currentTimeMillis(),
                userId = UUID.randomUUID().toString(),
                username = "test",
                email = "test@test.com",
                firstName = "Test",
                key = UUID.randomUUID().toString(),
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