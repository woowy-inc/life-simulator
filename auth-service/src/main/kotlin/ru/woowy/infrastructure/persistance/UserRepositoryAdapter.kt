package ru.woowy.infrastructure.persistance

import org.springframework.stereotype.Component
import ru.woowy.domain.model.UserRegisterRequest
import ru.woowy.domain.repository.UserRepository
import ru.woowy.infrastructure.mapping.asDomain
import ru.woowy.infrastructure.mapping.asEntity
import ru.woowy.security.User
import java.util.UUID

@Component
internal class UserRepositoryAdapter(
    private val userRepository: CrudUserRepository,
) : UserRepository {
    override fun findByUsername(username: String): User? = userRepository.findUserByUsername(username)?.asDomain()

    override fun findById(userId: UUID): User? = userRepository.findById(userId)?.get()?.asDomain()

    override fun addUser(request: UserRegisterRequest): User = userRepository.save(request.asEntity()).asDomain()
}