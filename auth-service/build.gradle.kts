plugins {
    kotlin("jvm")

    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.kotlin.jpa)
}

group = "ru.woowy"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common:common-domain"))
    implementation(libs.bundles.springboot.security.ecosystem)
    implementation(libs.migration.liquibase.core)
    implementation(libs.springboot.starter.liquibase)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.jackson)
    implementation(libs.jjwt.api)
    runtimeOnly(libs.jjwt.impl)
    runtimeOnly(libs.jjwt.gson)

    runtimeOnly(libs.postgresql.jdbc)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}