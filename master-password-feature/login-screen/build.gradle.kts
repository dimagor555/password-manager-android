plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
    implementation(projects.masterPasswordFeature.uiCore)
    implementation(projects.uiCore)

    implementation(AndroidX.biometric)
}