plugins {
    `kotlin-library`
}

dependencies {
    api(projects.masterPasswordFeature.domain)
    implementation(projects.validation)
    implementation(Libs.KotlinX.coroutinesCore)
}