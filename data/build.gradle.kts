@Suppress("DSL_SCOPE_VIOLATION")
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

    implementation(libs.retrofit)
    implementation(libs.moshi)

}