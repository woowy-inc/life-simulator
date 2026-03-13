plugins {
    kotlin("jvm")
}

group = "ru.woowy"
version = "0.2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    api(libs.bundles.springboot.security.ecosystem)

    api(libs.springboot.starter.oauth2.resource.server)
    api(libs.springboot.starter.springdoc.ui)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}