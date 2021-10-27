plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    api(projects.encryptionFeature.coreImpl)

    implementation(AndroidXSecurity.securityCrypto)
}