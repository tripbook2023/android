@file:Suppress("UnstableApiUsage")

plugins {
    id("com.tripbook.library")
}

android {
    namespace = "com.tripbook.base"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    api(libs.timber)
}