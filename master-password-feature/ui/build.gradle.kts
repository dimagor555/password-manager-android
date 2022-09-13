plugins {
    `compose-ui-library`
    `compose-screen`
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
//    implementation(projects.masterPasswordFeature.ui)
    implementation(projects.uiCore)

    implementation(Libs.AndroidX.biometric)

//    implementation(projects.uiValidation)
    implementation(projects.validation.ui)
    implementation(projects.validation.core)
}