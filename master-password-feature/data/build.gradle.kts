plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.domain)
    implementation(Kotlinx.coroutinesCore)
}