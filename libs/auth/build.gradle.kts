plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
}

android {
    namespace = "com.tripbook.tripbook.libs.auth"

    defaultConfig {
        manifestPlaceholders.putAll(
            mutableMapOf(
                "auth0Domain" to "dev-z2b4bazfo6o536tj.us.auth0.com",
                "auth0Scheme" to "demo"
            )
        )
    }
}

dependencies {
    api(libs.auth0)

    implementation(libs.coroutine)
}