plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
}

group = "ru.woowy"
version = "0.3.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    api(libs.bundles.springboot.kafka.ecosystem)

    implementation(libs.kotlin.serialization.protobuf)

    testImplementation(testFixtures(project(":common-domain")))
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}