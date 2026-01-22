pluginManagement {
    plugins {
        kotlin("jvm") version "2.3.0"
    }
}
rootProject.name = "life-simulator"

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

include(":common:common-utils")
include(":common:common-domain")
include(":common:common-events")
include(":time-service")