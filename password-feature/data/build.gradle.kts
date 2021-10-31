plugins {
    id(ScriptPlugins.androidLibrary)
}

dependencies {
    api(projects.passwordFeature.usecase)

    implementation(Room.runtime)
    implementation(Room.ktx)
    kapt(Room.compiler)
}