plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
}

group = "ru.woowy"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    implementation(libs.springboot.kafka.starter)
    implementation(libs.kotlin.serialization.protobuf)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}