package ru.woowy.infrastructure.persistence.repository

import org.springframework.cache.annotation.CacheEvict
import org.springframework.stereotype.Repository
import ru.woowy.domain.model.CacheName
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistence.jpa.JpaUserRepository
import ru.woowy.security.User
import java.util.UUID
import kotlin.jvm.optionals.getOrNull

@Repository
class UserRepositoryImpl(
    private val jpaUserRepository: JpaUserRepository,
) : UserRepository {
    override fun isUsernameExists(username: String): Boolean = jpaUserRepository.existsByUsernameIgnoreCase(username)

    override fun findByUsername(username: String): User? = jpaUserRepository.findUserByUsername(username)?.asDomain()

    override fun findById(userId: UUID): User? = jpaUserRepository.findById(userId).getOrNull()?.asDomain()

    override fun add(request: UserRegisterRequest): User = jpaUserRepository.save(request.asEntity()).asDomain()

    @CacheEvict(
        cacheNames = [
            CacheName.USER_BY_USERNAME,
            CacheName.USER_BY_USER_ID,
            CacheName.USERNAME_AVAILABILITY_BY_USERNAME,
        ],
    )
    override fun update(user: User): User = jpaUserRepository.save(user.asEntity()).asDomain()
}