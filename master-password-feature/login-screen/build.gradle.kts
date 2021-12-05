plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
    implementation(projects.masterPasswordFeature.biometry)
    implementation(projects.masterPasswordFeature.uiCore)
    implementation(projects.uiCore)
}