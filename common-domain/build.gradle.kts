plugins {
    id("java")
    kotlin("jvm")
    `java-test-fixtures`
}

group = "ru.woowy"
version = "0.18.0"

repositories {
    mavenCentral()
}

dependencies {
    api(libs.slf4j.api)
    api(libs.kotlinx.coroutines)

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}