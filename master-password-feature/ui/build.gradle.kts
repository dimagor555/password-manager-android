plugins {
    `compose-ui-library`
    `compose-screen`
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
    implementation(projects.masterPasswordFeature.data)
    implementation(projects.masterPasswordFeature.hashing)
    implementation(projects.uiCore)

    implementation(Libs.AndroidX.biometric)

    implementation(projects.validation.ui)
    implementation(projects.validation.core)
}