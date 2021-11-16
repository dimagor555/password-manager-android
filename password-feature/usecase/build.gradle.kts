plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.passwordFeature.domain)
    api(projects.encryptionFeature.domain)

    implementation(Kotlinx.coroutinesCore)
}