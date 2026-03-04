package ru.woowy.infrastructure

import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.context.TestConstructor
import org.testcontainers.junit.jupiter.Testcontainers

@DataJpaTest
@Testcontainers
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaRepositoryTest {
    companion object {
        @JvmStatic
        @DynamicPropertySource
        fun properties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.url", TestPostgresContainer.instance::getJdbcUrl)
            registry.add("spring.datasource.username", TestPostgresContainer.instance::getUsername)
            registry.add("spring.datasource.password", TestPostgresContainer.instance::getPassword)
        }
    }
}