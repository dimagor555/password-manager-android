plugins {
    `android-library`
}

dependencies {
    implementation(projects.passwordFeature.usecase)

    implementation(Libs.Room.runtime)
    implementation(Libs.Room.ktx)
    kapt(Libs.Room.compiler)
}