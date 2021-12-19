plugins {
    id(Conventions.kotlinLibrary)
}

dependencies {
    api(projects.encryptionFeature.domain)
}