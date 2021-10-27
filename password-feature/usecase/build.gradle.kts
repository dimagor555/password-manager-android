plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.passwordFeature.domain)
    implementation(projects.encryptionFeature.domain)

    implementation(Kotlinx.coroutinesCore)
}