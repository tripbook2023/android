@file:Suppress("UnstableApiUsage")

include(":libs:network")


include(":core:base")


pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { setUrl("https://jitpack.io")}
        maven { setUrl("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}
rootProject.name = "TripBook"
include(":presentation")
include(":data")
include(":domain")
include(":libs:auth")
include(":core:design")
