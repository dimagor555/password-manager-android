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
include(":desktop")
include(":core")
include(":root")

include(":ui-core")
include(":res-core")
include(":validation-core")

include(":encryption:symmetric")

include(":hashing")

include(":password-feature")
include(":password-generation-feature")
include(":master-password-feature")

include(":export-feature:api")
include(":export-feature:impl")
include(":export-feature:password-integration")
