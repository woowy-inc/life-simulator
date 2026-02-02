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
    implementation(project(":common:common-domain"))
    implementation(project(":common:common-events"))

    implementation(libs.bundles.kotlinx.ecosystem)
    implementation(libs.bundles.springboot.kafka.ecosystem)
    implementation(libs.springboot.starter.oauth2.resource.server)
    implementation(libs.springboot.starter.eureka.client)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}