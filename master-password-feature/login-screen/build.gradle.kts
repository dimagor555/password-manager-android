plugins {
    id(Conventions.uiAndroidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
    implementation(projects.masterPasswordFeature.uiCore)
    implementation(projects.uiCore)

    implementation(Libs.AndroidX.biometric)
}