plugins {
    id(Conventions.uiAndroidLibrary)
}

dependencies {
    implementation(projects.passwordFeature.usecase)
    implementation(projects.passwordFeature.uiCore)
    implementation(projects.uiCore)
}