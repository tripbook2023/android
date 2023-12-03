plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
    kotlin("kapt")
}

android {
    namespace = "com.tripbook.database"
}

dependencies {
    implementation(libs.datastore.preference)
    implementation(libs.coroutine)

    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    implementation(libs.datetime)
}