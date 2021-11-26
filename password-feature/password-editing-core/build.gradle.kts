plugins {
    id(ScriptPlugins.uiAndroidLibrary)
}

dependencies {
    api(projects.passwordFeature.usecase)
    api(projects.passwordFeature.uiCore)
    api(projects.uiCore)
}