package ru.woowy.auth.infrastructure.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import ru.woowy.auth.domain.model.TokenType

@Component
internal class JwtAuthenticationFilter(
    private val jwtTokenProvider: JwtTokenProvider,
    private val userDetailsService: UserDetailsService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        parseToken(request)?.let { token ->
            val tokenType = jwtTokenProvider.extractTokenType(token)
            if (tokenType != TokenType.ACCESS) {
                filterChain.doFilter(request, response)
                return
            }

            val username = jwtTokenProvider.extractUsername(token)
            val userDetails = userDetailsService.loadUserByUsername(username)

            val authentication =
                UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)

            SecurityContextHolder.getContext().authentication = authentication
        }

        filterChain.doFilter(request, response)
    }

    private fun parseToken(request: HttpServletRequest): String? {
        val token = request.getHeader("Authorization")

        return if (token?.startsWith("Bearer ") == true) {
            token.substring(7)
        } else {
            null
        }
    }
}