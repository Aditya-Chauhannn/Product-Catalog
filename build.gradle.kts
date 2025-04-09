// Top-level build file where you can add configuration options common to all sub-projects/modules.

val compose_version = "1.5.10"
val kotlin_version = "1.9.22"
val navigation_version = "2.7.6"
val coroutines_version = "1.7.3"
val lifecycle_version = "2.7.0"
val koin_version = "3.4.3"
val retrofit_version = "2.9.0"
val okhttp_version = "4.11.0"
val coil_version = "2.4.0"
val accompanist_version = "0.30.1"

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.jetbrains.kotlin.android) apply false
}
