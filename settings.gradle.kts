enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "password-manager-android"

include(":app", ":core")
include(":constants")
include(":password-validation")
include(
    ":password-feature",
    ":password-feature:domain",
    ":password-feature:usecase",
    ":password-feature:data"
)
include(
    ":encryption-feature",
    ":encryption-feature:domain",
    ":encryption-feature:core-impl",
    ":encryption-feature:android-impl"
)