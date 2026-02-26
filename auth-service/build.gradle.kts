plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.28.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))
    testImplementation(testFixtures(project(":common-domain")))
    implementation(project(":common-events"))

    implementation(libs.bundles.springboot.security.ecosystem)
    implementation(libs.bundles.springboot.kafka.ecosystem)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.vault.config)
    implementation(libs.springframework.vault.core)
    implementation(libs.migration.liquibase.core)
    implementation(libs.springboot.starter.liquibase)
    implementation(libs.springboot.starter.springdoc.ui)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.jackson)
    implementation(libs.kotlinx.coroutines)
    implementation(libs.jjwt.api)
    implementation(libs.kotlinx.datetime)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.gson)
    runtimeOnly(libs.postgresql.jdbc)

    testImplementation(libs.springboot.data.jpa.test)
    testImplementation(libs.springboot.testcontainers)
    testImplementation(libs.testcontainers.postgresql)
    testImplementation(libs.testcontainers.junit.jupiter)
    testImplementation(libs.mockk)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    mainClass.set("ru.woowy.AuthServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}