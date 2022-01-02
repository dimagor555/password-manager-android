plugins {
    `android-app`
}

dependencies {
    implementation(projects.encryptionFeature.androidImpl)

    implementation(projects.passwordFeature.data)

    implementation(projects.masterPasswordFeature.data)
    implementation(projects.masterPasswordFeature.hashing)

    implementation(projects.navigation)

    implementation(projects.uiCore)
}
