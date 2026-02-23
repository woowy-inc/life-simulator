package ru.woowy.infrastructure.persistance.adapter

import org.springframework.stereotype.Component
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.infrastructure.mapper.asDomain
import ru.woowy.infrastructure.mapper.asEntity
import ru.woowy.infrastructure.persistance.repository.CrudUserRepository
import ru.woowy.security.User
import java.util.UUID

@Component
internal class UserRepositoryAdapter(
    private val userRepository: CrudUserRepository,
) : UserRepository {
    override fun isUsernameExists(username: String): Boolean = userRepository.existsByUsername(username)

    override fun findByUsername(username: String): User? = userRepository.findUserByUsername(username)?.asDomain()

    override fun findById(userId: UUID): User? = userRepository.findById(userId)?.get()?.asDomain()

    override fun add(request: UserRegisterRequest): User = userRepository.save(request.asEntity()).asDomain()

    override fun update(user: User): User = userRepository.save(user.asEntity()).asDomain()
}