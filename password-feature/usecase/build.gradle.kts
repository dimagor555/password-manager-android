plugins {
    `kotlin-library`
}

dependencies {
    api(projects.passwordFeature.domain)
    api(projects.encryptionFeature.domain)

    implementation(projects.validation.core)

    implementation(Libs.KotlinX.coroutinesCore)
}