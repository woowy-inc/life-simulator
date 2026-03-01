package ru.woowy.domain.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import ru.woowy.security.UserId
import ru.woowy.security.UserRole

data class UserPrincipal(
    val userId: UserId,
    val login: String,
    val role: UserRole,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority> = listOf(SimpleGrantedAuthority(role.name))

    override fun getPassword(): String = ""

    override fun getUsername(): String = login
}