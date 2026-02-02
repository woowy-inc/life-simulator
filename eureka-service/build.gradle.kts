plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
}

group = "ru.woowy"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.springboot.starter.eureka.server)
    implementation(libs.springboot.starter.eureka.client)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}