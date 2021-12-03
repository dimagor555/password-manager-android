plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.encryptionFeature.coreImpl)

    implementation(AndroidXSecurity.securityCrypto)
}