plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordFeature.domain)
    implementation(projects.passwordFeature.uiCore)
    implementation(projects.uiCore)
}