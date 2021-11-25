plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.validation)

    api(Kotlinx.datetime)
}