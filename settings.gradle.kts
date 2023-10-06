@file:Suppress("UnstableApiUsage")

include(":libs:database")


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
        maven { setUrl("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/") }
    }
}
rootProject.name = "TripBook"
include(":presentation")
include(":data")
include(":domain")
include(":libs:auth")
include(":libs:network")
include(":core:design")
include(":core:base")
