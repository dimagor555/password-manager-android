plugins {
    id(Conventions.androidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.domain)
    implementation(Libs.KotlinX.coroutinesCore)
}