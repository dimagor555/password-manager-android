plugins {
    `android-app`
}

dependencies {
    implementation(projects.encryptionCore)

    implementation(projects.passwordFeature)

    implementation(projects.masterPasswordFeature)

    implementation(projects.root)

    implementation(projects.uiCore)
}
