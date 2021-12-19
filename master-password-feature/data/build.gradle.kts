plugins {
    `android-library`
}

dependencies {
    implementation(projects.masterPasswordFeature.domain)
    implementation(Libs.KotlinX.coroutinesCore)
}