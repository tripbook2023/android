plugins {
    id("com.tripbook.library")
    kotlin("kapt")
}

android {
    namespace = "com.tripbook.base"
    compileSdk = 34

    buildFeatures {
        dataBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    api(libs.timber)
}