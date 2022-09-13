enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "password-manager-android"

include(":app", ":core")
include(":navigation")
include(":ui-core")
include(
    ":validation",
    ":ui-validation"
)
include(
    ":password-feature",
    ":password-feature:domain",
    ":password-feature:usecase",
    ":password-feature:data",
    ":password-feature:ui"
)
include(
    ":encryption-feature",
    ":encryption-feature:domain",
    ":encryption-feature:core-impl",
    ":encryption-feature:android-impl"
)
include(
    ":password-generation-feature",
    ":password-generation-feature:domain",
    ":password-generation-feature:screen"
)
include(
    ":master-password-feature",
    ":master-password-feature:domain",
    ":master-password-feature:usecase",
    ":master-password-feature:data",
    ":master-password-feature:hashing",
    ":master-password-feature:ui",
)
