package ru.woowy.infrastructure.persistence.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertNull
import org.springframework.beans.factory.annotation.Autowired
import ru.woowy.JpaRepositoryTest
import ru.woowy.helper.randomUserRegisterRequest
import ru.woowy.infrastructure.persistence.jpa.JpaUserRepository
import ru.woowy.infrastructure.persistence.repository.UserRepositoryImpl
import ru.woowy.util.randomEmail
import ru.woowy.util.randomPassword
import ru.woowy.util.randomString
import ru.woowy.util.randomUUID
import ru.woowy.util.randomUserRole
import ru.woowy.util.randomUsername
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class UserRepositoryImplTest
    @Autowired
    constructor(
        jpaUserRepository: JpaUserRepository,
    ) : JpaRepositoryTest() {
        private val adapter = UserRepositoryImpl(jpaUserRepository)

        @Test
        fun `should return true if username exists`() {
            val request = randomUserRegisterRequest()
            adapter.add(request)

            val actual = adapter.isUsernameExists(request.username)

            assertTrue(actual)
        }

        @Test
        fun `should return false if username not exists`() {
            val request = randomUserRegisterRequest()
            adapter.add(request)

            val actual = adapter.isUsernameExists(randomUsername(4))

            assertFalse(actual)
        }

        @Test
        fun `should return user domain by username`() {
            val request = randomUserRegisterRequest()
            adapter.add(request)

            val actual = adapter.findByUsername(request.username)

            assertNotNull(actual)
            assertEquals(request.username, actual.username)
            assertEquals(request.email, actual.email)
            assertEquals(request.firstName, actual.firstName)
        }

        @Test
        fun `should return null if user not found by username`() {
            val username = randomUsername()
            adapter.add(randomUserRegisterRequest())

            val actual = adapter.findByUsername(username)

            assertNull(actual)
        }

        @Test
        fun `should return user domain if found by id`() {
            val user = adapter.add(randomUserRegisterRequest())
            val actual = adapter.findById(user.id)

            assertEquals(user, actual)
        }

        @Test
        fun `should return null if user not found by id`() {
            adapter.add(randomUserRegisterRequest())
            val actual = adapter.findById(randomUUID())

            assertNull(actual)
        }

        @Test
        fun `should add user and return domain`() {
            val request = randomUserRegisterRequest()
            val actual = adapter.add(request)

            assertNotNull(actual.id)
            assertEquals(request.username, actual.username)
            assertEquals(request.email, actual.email)
        }

        @Test
        fun `should update user and return updated domain`() {
            val user = adapter.add(randomUserRegisterRequest())
            val newUsername = randomUsername()
            val newEmail = randomEmail()
            val newPassword = randomPassword()
            val newFirstName = randomString()
            val newRole = randomUserRole(exclude = user.role)
            val newIsEmailVerified = !user.isEmailVerified

            val actual =
                adapter.update(
                    user.copy(
                        username = newUsername,
                        email = newEmail,
                        password = newPassword,
                        firstName = newFirstName,
                        role = newRole,
                        isEmailVerified = newIsEmailVerified,
                    ),
                )

            assertEquals(user.id, actual.id)
            assertEquals(newUsername, actual.username)
            assertEquals(newEmail, actual.email)
            assertEquals(newPassword, actual.password)
            assertEquals(newFirstName, actual.firstName)
            assertEquals(newRole, actual.role)
            assertEquals(newIsEmailVerified, actual.isEmailVerified)
        }
    }