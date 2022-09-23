package convention

plugins {
    id("com.android.library")
    id("convention.android")
    id("convention.compose-ui")
    id("org.jetbrains.compose")
}

dependencies {
    implementation(Libs.MviCompose.core)
    implementation(Libs.MviCompose.android)

    implementation(Libs.localizedString)
}