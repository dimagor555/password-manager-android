plugins {
    id(ScriptPlugins.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.passwordValidation)

    api(Kotlinx.datetime)
}