plugins {
    id(Conventions.kotlinLibrary)
}

dependencies {
    api(projects.masterPasswordFeature.domain)
    implementation(projects.validation)
    implementation(Libs.KotlinX.coroutinesCore)
}