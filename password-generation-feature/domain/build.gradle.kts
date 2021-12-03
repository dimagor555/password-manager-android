plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    implementation(projects.constants)
    implementation(Kotlinx.coroutinesCore)
}