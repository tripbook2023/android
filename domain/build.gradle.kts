plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
}

android {
    namespace = "com.tripbook.tripbook.domain"
}

dependencies {
    implementation(libs.coroutine)
    implementation(libs.paging)
}