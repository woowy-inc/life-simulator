plugins {
    kotlin("jvm")
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
    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}