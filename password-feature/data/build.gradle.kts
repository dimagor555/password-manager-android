plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    implementation(projects.passwordFeature.usecase)

    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)
}