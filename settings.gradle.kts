pluginManagement {
    plugins {
        id("org.jetbrains.kotlin.android") version "1.9.22"
        id("com.android.application") version "8.4.1" // Adjust to match your AGP version
    }
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Product Catalog"
include(":app")
