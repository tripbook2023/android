plugins {
    id("com.tripbook.library")
    id("com.tripbook.hilt")
}

android {
    namespace = "com.tripbook.tripbook.data"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":libs:network"))
    implementation(project(":libs:database"))

    implementation(libs.paging)

    implementation(libs.retrofit)
    implementation(libs.moshi)
    implementation(libs.timber)
    implementation("androidx.paging:paging-common-android:+")
}