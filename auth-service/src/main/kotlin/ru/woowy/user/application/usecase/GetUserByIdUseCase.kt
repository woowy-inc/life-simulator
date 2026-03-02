package ru.woowy.user.application.usecase

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.woowy.common.cache.CacheName
import ru.woowy.security.User
import ru.woowy.user.domain.repository.UserRepository
import java.util.UUID

@Service
@Transactional
internal class GetUserByIdUseCase(
    private val userRepository: UserRepository,
) {
    @Cacheable(cacheNames = [CacheName.USER_BY_USER_ID], key = "#userId")
    operator fun invoke(userId: UUID): User? = userRepository.findById(userId)
}