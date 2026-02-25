plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.27.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    implementation(libs.springboot.starter.gateway)
    implementation(libs.springboot.starter.loadbalancer)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.kotlin.reflect)
    implementation(libs.springboot.starter.springdoc.ui)

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