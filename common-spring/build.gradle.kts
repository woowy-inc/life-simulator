plugins {
    kotlin("jvm")
}

group = "ru.woowy"
version = "0.1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    api(libs.springboot.starter.openfeign)
    api(libs.springboot.starter.web)

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}