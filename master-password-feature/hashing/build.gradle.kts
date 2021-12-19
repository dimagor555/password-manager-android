plugins {
    id(Conventions.androidLibrary)
}

dependencies {
    implementation(projects.masterPasswordFeature.domain)
    implementation(Libs.argon2kt)
    implementation(Libs.KotlinX.coroutinesCore)
}