plugins {
    id(Conventions.androidLibrary)
}

dependencies {
    implementation(projects.encryptionFeature.coreImpl)

    implementation(Libs.AndroidX.securityCrypto)
}