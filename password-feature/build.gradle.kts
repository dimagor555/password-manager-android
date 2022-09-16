plugins {
    `compose-ui-library`
    `compose-screen`
}

dependencies {
    implementation(Libs.KotlinX.coroutinesCore)
    implementation(Libs.Koin.core)
    implementation(Libs.Room.runtime)
    implementation(Libs.Room.ktx)
    kapt(Libs.Room.compiler)
    api(Libs.KotlinX.datetime)

    implementation(projects.validationCore)

    implementation(projects.uiCore)

    api(projects.encryptionCore)
}