plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))
    implementation(project(":common-events"))
    implementation(project(":common-security"))
    implementation(project(":common-spring"))

    implementation(libs.bundles.kotlinx.ecosystem)

    implementation(libs.springboot.kafka.starter)
    implementation(libs.springboot.starter.data.jpa)
    implementation(libs.springboot.starter.data.redis)
    implementation(libs.springboot.starter.liquibase)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.openfeign)
    implementation(libs.springboot.starter.websocket)

    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.springboot.kafka)

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
    mainClass.set("ru.woowy.EngineServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}