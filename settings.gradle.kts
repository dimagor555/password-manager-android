enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "password-manager-android"

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenLocal()
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":app")
include(":navigation")

include(":ui-core")
include(":validation-core")
include(":encryption-core")

include(":password-feature")
include(":password-generation-feature")
include(":master-password-feature")