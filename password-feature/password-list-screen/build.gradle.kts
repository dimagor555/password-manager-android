plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordFeature.data)
    implementation(projects.encryptionFeature.androidImpl)
    implementation(projects.passwordFeature.uiCore)
    implementation(projects.uiCore)
}