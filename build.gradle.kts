// Top-level build file where you can add configuration options common to all sub-projects/modules.
@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.plugin) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.banManes) apply false
    alias(libs.plugins.littleRobots) apply false
    alias(libs.plugins.gms) apply false
}