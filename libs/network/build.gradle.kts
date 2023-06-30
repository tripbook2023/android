plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
}

android {
    namespace = "com.tripbook.libs.network"
}

dependencies {
    implementation(project(":libs:database"))

    implementation(libs.retrofit)
    implementation(libs.moshi)

    implementation(libs.coroutine)
}