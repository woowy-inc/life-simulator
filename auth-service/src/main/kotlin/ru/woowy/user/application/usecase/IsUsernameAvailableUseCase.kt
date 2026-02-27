package ru.woowy.user.application.usecase

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.woowy.common.cache.CacheName
import ru.woowy.user.domain.model.UsernameAvailableDto
import ru.woowy.user.domain.repository.UserRepository

@Service
internal class IsUsernameAvailableUseCase(
    private val userRepository: UserRepository,
) {
    @Cacheable(cacheNames = [CacheName.USERNAME_AVAILABILITY_BY_USERNAME], key = "#username")
    operator fun invoke(username: String): UsernameAvailableDto {
        val isAvailable = !userRepository.isUsernameExists(username)

        return UsernameAvailableDto(username = username, isAvailable = isAvailable)
    }
}