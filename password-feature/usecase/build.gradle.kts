plugins {
    `kotlin-library`
}

dependencies {
    api(projects.passwordFeature.domain)
    api(projects.encryptionFeature.domain)

    implementation(Libs.KotlinX.coroutinesCore)
}