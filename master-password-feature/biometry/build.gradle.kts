plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.usecase)
    implementation(Kotlinx.coroutinesCore)
    implementation(AndroidX.biometric)
}