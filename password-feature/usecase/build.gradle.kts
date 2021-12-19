plugins {
    id(Conventions.kotlinLibrary)
}

dependencies {
    api(projects.core)
    api(projects.passwordFeature.domain)
    api(projects.encryptionFeature.domain)

    implementation(Libs.KotlinX.coroutinesCore)
}