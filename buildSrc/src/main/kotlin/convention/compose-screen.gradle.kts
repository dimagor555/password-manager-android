package convention

plugins {
    id("com.android.library")
    id("convention.android")
    id("convention.compose-ui")
}

dependencies {
    implementation(Libs.MviCompose.core)
    implementation(Libs.MviCompose.android)

    implementation(Libs.Navigation.hiltCompose)

    implementation(Libs.localizedString)
}