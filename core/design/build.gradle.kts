plugins {
    id("com.tripbook.library")
    kotlin("kapt")
}

android {
    namespace = "com.tripbook.tripbook.core.design"

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.glide)

    implementation(libs.appcompat)
}