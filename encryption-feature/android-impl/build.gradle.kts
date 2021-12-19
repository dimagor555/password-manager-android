plugins {
    `android-library`
}

dependencies {
    implementation(projects.encryptionFeature.coreImpl)

    implementation(Libs.AndroidX.securityCrypto)
}