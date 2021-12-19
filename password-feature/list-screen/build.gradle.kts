plugins {
    `compose-screen`
}

dependencies {
    implementation(projects.passwordFeature.usecase)
    implementation(projects.passwordFeature.uiCore)
    implementation(projects.uiCore)
}