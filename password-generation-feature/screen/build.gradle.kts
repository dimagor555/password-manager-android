plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordGenerationFeature.domain)
    implementation(projects.uiCore)
}