plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.spring.dependency.management)
}

group = "ru.woowy"
version = "0.4.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))
    implementation(project(":common-events"))

    implementation(libs.kotlinx.coroutines)
    implementation(libs.kotlinx.coroutines.reactor)
    implementation(libs.bundles.springboot.kafka.ecosystem)
    implementation(libs.springboot.starter.eureka.client)
    implementation(libs.springboot.starter.mail)
    implementation(libs.springboot.starter.thymeleaf)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

springBoot {
    mainClass.set("ru.woowy.NotificationServiceApplicationKt")
}

tasks.named<Jar>("jar") {
    enabled = false
}

tasks.test {
    useJUnitPlatform()
}