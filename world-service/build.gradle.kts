plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.3.0"

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(project(":common-domain"))
    implementation(project(":common-events"))
    implementation(project(":common-security"))
    implementation(project(":common-spring"))

    implementation(libs.springboot.starter.web)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.liquibase)
    implementation(libs.springboot.starter.data.jpa)
    implementation(libs.springboot.starter.cache)
    implementation(libs.springboot.starter.validation)

    implementation(libs.bundles.kotlinx.ecosystem)
    implementation(libs.migration.liquibase.core)
    implementation(libs.kotlin.jackson)
    implementation(libs.kotlinx.coroutines)
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
    mainClass.set("ru.woowy.WorldServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}