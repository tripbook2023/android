@file:Suppress("UnstableApiUsage", "DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.kotlin.plugin)
    alias(libs.plugins.android.application)
    id("com.tripbook.hilt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.tripbook.tripbook"
    compileSdk = libs.versions.targetSDK.get().toInt()
    buildToolsVersion = libs.versions.buildTools.get()

    defaultConfig {
        applicationId = "com.tripbook.tripbook"
        minSdk = libs.versions.minSDK.get().toInt()
        targetSdk = libs.versions.targetSDK.get().toInt()
        versionCode = libs.versions.versionCode.get().toInt()
        versionName = libs.versions.versionName.get()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        manifestPlaceholders.putAll(
            mutableMapOf(
                "auth0Domain" to "dev-z2b4bazfo6o536tj.us.auth0.com",
                "auth0Scheme" to "demo"
            )
        )
    }

    buildFeatures {
        viewBinding = true
        dataBinding = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(project(":data")) // 쓰이진 않음 ( Hilt 전용 )
    implementation(project(":domain"))
    implementation(project(":libs:auth"))
    implementation(project(":libs:network"))
    implementation(project(":core:base"))
    implementation(project(":core:design"))

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.retrofit)

    val firebaseBom = platform(libs.firebase.bom)
    implementation(firebaseBom)
    implementation(libs.firebase.analytics)

    implementation(libs.activity.ktx)
    implementation(libs.fragment.ktx)
    implementation(libs.navigation)
    implementation(libs.navigation.ui)

    implementation(libs.circleimageview)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.espresso.core)
}