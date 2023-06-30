plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
}

android {
    namespace = "com.tripbook.database"
}

dependencies {
    implementation(libs.datastore.preference)
    implementation(libs.coroutine)
}