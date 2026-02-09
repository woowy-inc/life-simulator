plugins {
    kotlin("jvm")
}

group = "ru.woowy"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common-domain"))

    testImplementation(kotlin("test"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}