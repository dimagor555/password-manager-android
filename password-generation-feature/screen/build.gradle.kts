plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordGenerationFeature.domain)
    implementation(projects.uiCore)
    implementation(MviCompose.core)
    implementation(MviCompose.android)
}