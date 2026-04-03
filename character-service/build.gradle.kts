plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.4.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))
    implementation(project(":common-events"))
    implementation(project(":common-security"))
    implementation(project(":common-spring"))

    implementation(libs.springboot.starter.web)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.oauth2.resource.server)
    implementation(libs.springboot.starter.liquibase)
    implementation(libs.springboot.starter.data.jpa)
    implementation(libs.springboot.starter.springdoc.ui)
    implementation(libs.springboot.starter.cache)
    implementation(libs.springboot.starter.validation)
    implementation(libs.springboot.starter.openfeign)
    implementation(libs.springboot.starter.resilience4j)

    implementation(libs.bundles.springboot.kafka.ecosystem)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.migration.liquibase.core)
    implementation(libs.kotlin.jackson)
    implementation(libs.caffeine)

    runtimeOnly(libs.postgresql.jdbc)

    testImplementation(libs.bundles.test.jpa.ecosystem)
    testImplementation(libs.mockk)
    testImplementation(testFixtures(project(":common-domain")))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    buildInfo()
    mainClass.set("ru.woowy.CharacterServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}