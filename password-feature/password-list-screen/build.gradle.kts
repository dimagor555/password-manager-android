plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordFeature.data)
    implementation(projects.encryptionFeature.androidImpl)
    implementation(projects.uiCore)
}