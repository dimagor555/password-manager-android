plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.domain)
    implementation(Hashing.argon2)
    implementation(Kotlinx.coroutinesCore)
}