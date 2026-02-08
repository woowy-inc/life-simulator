plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common:common-domain"))

    implementation(libs.springboot.starter.gateway)
    implementation(libs.springboot.starter.eureka.client)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    mainClass.set("ru.woowy.GatewayServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}