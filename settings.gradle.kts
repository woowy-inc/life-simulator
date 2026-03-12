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

include(":common-domain")
include(":common-events")
include(":common-security")

include(":eureka-service")
include(":gateway-service")
include(":auth-service")
include(":character-service")
include(":world-service")
include(":notification-service")