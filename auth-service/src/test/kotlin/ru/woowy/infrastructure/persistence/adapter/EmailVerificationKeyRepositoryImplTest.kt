package ru.woowy.infrastructure.persistence.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.JpaRepositoryTest
import ru.woowy.helper.randomEmailVerificationKey
import ru.woowy.helper.randomUserRegisterRequest
import ru.woowy.infrastructure.persistence.jpa.JpaEmailVerificationKeyRepository
import ru.woowy.infrastructure.persistence.jpa.JpaUserRepository
import ru.woowy.infrastructure.persistence.repository.EmailVerificationKeyRepositoryImpl
import ru.woowy.infrastructure.persistence.repository.UserRepositoryImpl
import ru.woowy.util.randomString
import kotlin.test.assertNull

class EmailVerificationKeyRepositoryImplTest
    @Autowired
    constructor(
        jpaUserRepository: JpaUserRepository,
        emailVerificationTokenRepository: JpaEmailVerificationKeyRepository,
    ) : JpaRepositoryTest() {
        private val userAdapter = UserRepositoryImpl(jpaUserRepository)
        private val adapter = EmailVerificationKeyRepositoryImpl(emailVerificationTokenRepository)

        @Test
        fun `should return email verification key domain by key id`() {
            val user = userAdapter.add(randomUserRegisterRequest())
            val expected = randomEmailVerificationKey(user = user)
            adapter.save(expected)

            val actual = adapter.findByKey(expected.key)

            assertEquals(expected, actual)
        }

        @Test
        fun `should return null if email verification key not found`() {
            val user = userAdapter.add(randomUserRegisterRequest())
            val expected = randomEmailVerificationKey(user = user)
            adapter.save(expected)

            val actual = adapter.findByKey(randomString())
            assertNull(actual)
        }

        @Test
        fun `should save email verification key`() {
            val user = userAdapter.add(randomUserRegisterRequest())
            val expected = randomEmailVerificationKey(user = user)
            adapter.save(expected)

            val actual = adapter.findByKey(expected.key)
            assertEquals(expected, actual)
        }
    }