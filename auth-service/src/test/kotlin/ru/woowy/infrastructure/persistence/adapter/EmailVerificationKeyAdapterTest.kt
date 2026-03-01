package ru.woowy.infrastructure.persistence.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.RepositoryTest
import ru.woowy.helper.randomEmailVerificationKey
import ru.woowy.helper.randomUserRegisterRequest
import ru.woowy.user.infrastructure.persistence.adapter.EmailVerificationKeyAdapter
import ru.woowy.user.infrastructure.persistence.adapter.UserRepositoryAdapter
import ru.woowy.user.infrastructure.persistence.repository.CrudEmailVerificationKeyRepository
import ru.woowy.user.infrastructure.persistence.repository.CrudUserRepository
import ru.woowy.util.randomString
import kotlin.test.assertNull

internal class EmailVerificationKeyAdapterTest
    @Autowired
    constructor(
        crudUserRepository: CrudUserRepository,
        emailVerificationTokenRepository: CrudEmailVerificationKeyRepository,
    ) : RepositoryTest() {
        private val userAdapter = UserRepositoryAdapter(crudUserRepository)
        private val adapter = EmailVerificationKeyAdapter(emailVerificationTokenRepository)

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