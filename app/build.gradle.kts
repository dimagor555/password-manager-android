plugins {
    `android-app`
}

dependencies {
    implementation(projects.encryptionCore)

    implementation(projects.passwordFeature)

    implementation(projects.masterPasswordFeature)

    implementation(projects.navigation)

    implementation(projects.uiCore)
}
