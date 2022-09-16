plugins {
    `compose-ui-library`
    `compose-screen`
}

dependencies {
    implementation(Libs.KotlinX.coroutinesCore)

    implementation(Libs.argon2kt)

    implementation(projects.uiCore)

    implementation(Libs.AndroidX.biometric)

    implementation(projects.validationCore)
    implementation(projects.validationCore)
}