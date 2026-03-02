package ru.woowy.user.infrastructure.persistence.adapter

import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Component
import ru.woowy.common.cache.CacheName
import ru.woowy.security.User
import ru.woowy.user.domain.model.UserRegisterRequest
import ru.woowy.user.domain.repository.UserRepository
import ru.woowy.user.infrastructure.mapper.asDomain
import ru.woowy.user.infrastructure.mapper.asEntity
import ru.woowy.user.infrastructure.persistence.repository.CrudUserRepository
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Component
internal class UserRepositoryAdapter(
    private val userRepository: CrudUserRepository,
) : UserRepository {
    override fun isUsernameExists(username: String): Boolean = userRepository.existsByUsernameIgnoreCase(username)

    override fun findByUsername(username: String): User? = userRepository.findUserByUsername(username)?.asDomain()

    override fun findById(userId: UUID): User? = userRepository.findById(userId).getOrNull()?.asDomain()

    override fun add(request: UserRegisterRequest): User = userRepository.save(request.asEntity()).asDomain()

    @CacheEvict(
        cacheNames = [
            CacheName.USER_BY_USERNAME,
            CacheName.USER_BY_USER_ID,
            CacheName.USERNAME_AVAILABILITY_BY_USERNAME,
        ],
    )
    override fun update(user: User): User = userRepository.save(user.asEntity()).asDomain()
}