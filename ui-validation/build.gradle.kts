plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.validation)
    implementation(LocalizedString.lib)
}