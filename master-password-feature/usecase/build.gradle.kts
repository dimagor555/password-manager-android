plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.masterPasswordFeature.domain)
    implementation(Kotlinx.coroutinesCore)
}