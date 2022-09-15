plugins {
    `compose-ui-library`
    `compose-screen`
}

dependencies {
    implementation(projects.uiCore)

    implementation(projects.passwordFeature.usecase)
    implementation(projects.passwordFeature.data)
    implementation(projects.validation.core)
    implementation(projects.validation.ui)
}