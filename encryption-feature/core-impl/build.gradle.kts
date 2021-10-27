plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.encryptionFeature.domain)
}