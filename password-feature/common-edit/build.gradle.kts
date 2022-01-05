plugins {
    `compose-screen`
}

dependencies {
    api(projects.passwordFeature.usecase)
    api(projects.passwordFeature.uiCore)
    api(projects.uiCore)
    implementation(projects.uiValidation)
}