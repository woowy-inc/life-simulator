package ru.woowy.infrastructure.holder

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import ru.woowy.domain.holder.NeedHolder
import ru.woowy.domain.model.Need
import ru.woowy.id.CharacterId
import java.util.UUID

@Component
class NeedHolderImpl(
    private val needRedisTemplate: RedisTemplate<String, Need>,
) : NeedHolder {
    override suspend fun get(characterId: CharacterId): Need? = needRedisTemplate.opsForValue().get(characterId.toKey())

    override suspend fun push(
        characterId: CharacterId,
        need: Need,
    ) {
        needRedisTemplate.opsForValue().set(characterId.toKey(), need)
    }

    override suspend fun popAll(): Map<CharacterId, Need> {
        val keys = needRedisTemplate.keys("need:*")
        if (keys.isNullOrEmpty()) return emptyMap()

        return keys
            .mapNotNull { key ->
                val need = needRedisTemplate.opsForValue().get(key) ?: return@mapNotNull null
                key.toCharacterId() to need
            }.toMap()
    }

    private fun CharacterId.toKey() = "need:$this"

    private fun String.toCharacterId(): CharacterId = UUID.fromString(this.removePrefix("need:"))
}