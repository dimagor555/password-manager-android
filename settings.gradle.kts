enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "password-manager-android"

include(":app")
include(":navigation")

include(":ui-core")
include(":validation-core")
include(":encryption-core")

include(":password-feature")
include(":password-generation-feature")
include(":master-password-feature")
