plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordGenerationFeature.domain)
    implementation(projects.mvi.android)
    implementation(projects.uiCore)
}