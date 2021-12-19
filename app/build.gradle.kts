plugins {
    `android-app`
}

dependencies {
    implementation(projects.encryptionFeature.androidImpl)

    implementation(projects.passwordFeature.data)
    implementation(projects.passwordFeature.listScreen)
    implementation(projects.passwordFeature.detailsScreen)
    implementation(projects.passwordFeature.creationScreen)
    implementation(projects.passwordFeature.editingScreen)

    implementation(projects.passwordGenerationFeature.screen)

    implementation(projects.masterPasswordFeature.data)
    implementation(projects.masterPasswordFeature.hashing)
    implementation(projects.masterPasswordFeature.loginScreen)

    implementation(projects.uiCore)
}
