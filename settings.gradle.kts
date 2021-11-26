enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "password-manager-android"

include(":app", ":core")
include(":ui-core")
include(":constants")
include(":validation")
include(
    ":password-feature",
    ":password-feature:domain",
    ":password-feature:usecase",
    ":password-feature:data",
    ":password-feature:ui-core",
    ":password-feature:list-screen",
    ":password-feature:details-screen",
    ":password-feature:editing-core",
    ":password-feature:creation-screen",
    ":password-feature:editing-screen"
)
include(
    ":encryption-feature",
    ":encryption-feature:domain",
    ":encryption-feature:core-impl",
    ":encryption-feature:android-impl"
)