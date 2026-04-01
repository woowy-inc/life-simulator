plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    implementation(libs.springboot.starter.gateway)
    implementation(libs.springboot.starter.loadbalancer)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.webflux)
    implementation(libs.springboot.starter.security)
    implementation(libs.springboot.starter.oauth2.resource.server)

    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.kotlin.reflect)

    testImplementation(kotlin("test"))
    testImplementation(libs.mockk)
    testImplementation(libs.springboot.starter.test)
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    buildInfo()
    mainClass.set("ru.woowy.GatewayServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}